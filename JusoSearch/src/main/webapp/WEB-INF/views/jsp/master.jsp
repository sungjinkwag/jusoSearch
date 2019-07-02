<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="<c:url value="/resource/js/jquery-3.2.1.js" />"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=bffea527e74cbb89c5ae698f84cf5400"></script>
<title>카카오 주소 검색</title>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 5px;
  text-align: left;    
}
.pagination {
  display: inline-block;
}

.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
}

.pagination a.active {
  background-color: #4CAF50;
  color: white;
  border-radius: 5px;
}

.pagination a:hover:not(.active) {
  background-color: #ddd;
  border-radius: 5px;
}

.label {margin-bottom: 96px;}
.label * {display: inline-block;vertical-align: top;}
.label .left {display: inline-block;height: 24px;overflow: hidden;vertical-align: top;width: 7px;}
.label .center {background: black;display: inline-block;height: 48px;font-size: 12px;line-height: 24px;color:yellow;}
.label .right {display: inline-block;height: 24px;overflow: hidden;width: 6px;}

</style>
</head>

<body>
	<div id="loginBox" style="display:none;"></div>
	<div id="searchBox" style="display:none;"></div>
</body>
<script type="text/javascript">
//<![CDATA[
	$(document).ready(function(){
		var _isLoginYn = "${isLogin}";
		if(_isLoginYn == "Y"){
			getSearchHtml();
		} else {
			getLoginHtml();
		}
	});

	function getSearchHtml(){
		$.ajax({
			type:'GET',
			url:'/searchJuso',
			success:function(data){
				$('#searchBox').append(data).show();
				$('#loginBox').hide().html('');
			},
			error:function(xhr, status, error){
				alert('주소 검색 페이지를 불러오는데 오류가 발생하였습니다.');
			}
		});
	}

	function getLoginHtml(){
		$.ajax({
			type:'GET',
			url:'/login',
			success:function(data){
				$('#loginBox').append(data).show();
				$('#searchBox').hide().html('');
			},
			error:function(){
				alert('로그인 페이지를 불러오는데 오류가 발생하였습니다.');
			}
		});
	}

	function login(){
		$.ajax({
			type:'GET',
			url:'/login/do',
			data: $("#loginForm").serialize(),
			success:function(data){
				if(data){
					alert('로그인에 성공하였습니다.');
					getSearchHtml();
				} else {
					alert('로그인 정보가 올바르지 않습니다.');
					$('#id').val('');
					$('#pw').val('');
				}
			},
			error:function(){
				alert('로그인에 실패하였습니다.');
			}
		});
	}

	function searchKeyword(flag){
		if(flag=='1'){
			$('#currentPage').val('1');
		}
		var pageNo = parseInt($('#currentPage').val());
		if(flag===undefined){
			if(pageNo < 1){
				return;
			}
		}
		
		$('#listBox').html('');
		$('#detailBox').html('');
		var resultAjax = false;
		// 현재 페이지값 세팅
		$.ajax({
			type:'GET',
			url:'/searchKeyword',
			async:false,
			data: $("#keywordForm").serialize(),
			success:function(data){
				resultAjax = true;
				var totalPageCnt;
				// 주소 목록 그리기
				$.each(JSON.parse(data), function(key, value){
					if(key == "documents"){
						for(var i=0;i<value.length;i++){
							if(i==0){
								$('#listBox').append(
										'<table id="listTable">'+
											'<tr>'+
												'<th colspan="2">장소명</th>'+
											'</tr>');
							}
							$('#listTable').append(
									'<tr><td>'+value[i].place_name+'</td>'
									+'<td><button type="button" onclick="detailJuso('+value[i].id+','+value[i].y+','+value[i].x+');">상세보기</button></td></tr>');
							$('#detailBox').append('<div id='+value[i].id+' style="display:none;">'+
									'장소명 : '+value[i].place_name+'<br>'+
									'도로명주소 : '+value[i].road_address_name+'<br>'+
									'지번주소 : '+value[i].address_name+'<br>'+
									'전화번호 : '+value[i].phone+'<br>'+
									 '다음지도 바로가기 : '+'<a href="https://map.kakao.com/link/map/'+value[i].id+'">https://map.kakao.com/link/map/'+value[i].id+'</a><br>'+
									'<div id="map_'+value[i].id+'" style="width:500px;height:400px;"></div>'+
									'<button type="button" onclick="backToList('+value[i].id+');">목록으로 되돌아가기</button>'+
									'<input type="hidden" id="name_'+value[i].id+'" value="'+value[i].place_name+'">'+
									'<input type="hidden" id="adrName_'+value[i].id+'" value="'+value[i].road_address_name+'">'+
									'</div>');
							if(i == value.length-1){
								$('#listBox').append('</table>');
							}
						}
					}
					if(key == "meta"){
						var totalCnt = parseInt(value.total_count);
						totalPageCnt = Math.ceil(totalCnt/10);
						$('#totalPage').val(totalPageCnt);
					}
				});
				$('#pagingBox').show();
				$('#listBox').show();
				activePage(totalPageCnt);		
			},
			error:function(xhr, status, error){
				alert('키워드 검색 페이지 이동에 실패하였습니다.');
			}
		});
		if(resultAjax){
			refreshMyKeyList();
			refreshPopKeyList();
		}
	}	

	function detailJuso(id,lng,lat){
		$('#listBox').hide();
		$('#pagingBox').hide();
		$('#'+id).show();
		var container = document.getElementById('map_'+id);
		var options = {
			center: new kakao.maps.LatLng(lng, lat),
			level: 3
		};
		var map = new kakao.maps.Map(container, options);

		var placeName = $('#name_'+id).val();
		var addressName = $('#adrName_'+id).val();
		// 커스텀 오버레이
		var content = '<div class ="label"><span class="left"></span><span class="center">'+placeName+'<br>'+addressName+'</span><span class="right"></span></div>';
		var position = new kakao.maps.LatLng(lng, lat);
		var customOverlay = new kakao.maps.CustomOverlay({
		    position: position,
		    content: content   
		});
		customOverlay.setMap(map);
	}

	function backToList(id){
		$('#'+id).hide();
		$('#listBox').show();
		$('#pagingBox').show();
	}
 
	function activePage(_totalPageCnt){
        $(function () {
            window.pagObj = $('#pagination').twbsPagination({
                totalPages: _totalPageCnt,
                visiblePages: 10,
                onPageClick: function (event, page) {
                    $('#currentPage').val(page);
                    searchKeyword();
                }
            }).on('page', function (event, page) {
            });
        });
	}

	function refreshMyKeyList(){
		$.ajax({
			type:'GET',
			url:'/myKeyList/get',
			async:false,
			success:function(data){
				$('.myKeyListTr').remove();
				var cnt = 1;
				$.each(JSON.parse(data), function(key, value){
					var month = parseInt(value.regTime.monthValue);
					var day = parseInt(value.regTime.dayOfMonth);
					if(month < 10){
						month = '0'+month;
					}
					if(day < 10){
						day = '0'+day;
					}
					var time = value.regTime.year+'-'+
									month+'-'+day+'T'+value.regTime.hour+':'+
									value.regTime.minute+':'+value.regTime.second;
					$('#myKeyListTable').append(
							'<tr class="myKeyListTr">'+
								'<td>'+cnt+'</td>'+
								'<td>'+value.keyword+'</td>'+
								'<td>'+time+'</td>'+
							'</tr>'
					);
					cnt += 1;
				});
			},
			error:function(){
				alert('나의 검색 키워드 데이터를 불러오는데 오류가 발생하였습니다.');
			}
		});
	}

	function refreshPopKeyList(){
		$.ajax({
			type:'GET',
			url:'/popKeyList/get',
			async:false,
			success:function(data){
				$('.popKeyListTr').remove();
				var cnt = 1;
				$.each(JSON.parse(data), function(key, value){
					$('#popKeyListTable').append(
							'<tr class="popKeyListTr">'+
								'<td>'+cnt+'</td>'+
								'<td>'+value.keyword+'</td>'+
								'<td>'+value.cnt+'</td>'+
							'</tr>'
					);
					cnt += 1;
				});
			},
			error:function(){
				alert('인기 검색 키워드 데이터를 불러오는데 오류가 발생하였습니다.');
			}
		});
	}

	function logout(){
		location.href='/logout';
	}
//]]>
</script>
</html>