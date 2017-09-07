/**
 * Created by yangjian on 17-8-15.
 */

(function($) {

	//选中座位
	$.fn.selectSeat = function() {
		$(this).find('span').addClass("selected");
	}

	//取消选中座位
	$.fn.unselectSeat = function() {
		$(this).find('span').removeClass("selected");
	}

	//判断座位是否被选中
	$.fn.hasSelected = function() {
		return $(this).find('span').hasClass("selected");
	}

	// 加载 css 文件
	var js = document.scripts, script = js[js.length - 1], jsPath = script.src;
	var cssPath = jsPath.substring(0, jsPath.lastIndexOf("/") + 1)+"css/seat.css"
	$("head:eq(0)").append('<link href="'+cssPath+'" rel="stylesheet" type="text/css" />');

	var __seats__ = function(options) {

		var defaults = {
			box: "#box",
			rows : 20, //座位排数
			cols : 30, //座位列数
			size : 15, //座位的尺寸，单位：像素
			thre_id : 0, //剧场ID
			hall_id : 0, //厅ID
			movie_id : 0, //电影场次ID
			step : 1, //操作步骤，1：排座，2：设置票价，3：购票
			onSelected : function(seat) { //选中座位时候的回调，仅当 step = 3(购票)时有效
				console.log(seat);
			},
			onUnselected : function(seat) { //取消座位的时候回调
				console.log(seat);
			},
			onerror : function(msg) {

			},
			maxTicketNumber : 5, //最多购票数量
			selected : {}, //已选中的座位
			datas : {}
		};
		options = $.extend(defaults, options);
		var seats = {};
		var o = {};
		var ticketNumber = 0; //已购票数
		if ( getObjLength(options.datas) > 0 ) {
			seats = options.datas;
		}
		var seatColors = {};

		//创建元素
		function create() {
			var $table = $('<table class="seat-table animated-seat" cellpadding="0" cellspacing="0"></table>');
			for (var r = 1; r <= options.rows; r++) {
				var $tr = $('<tr></tr>');
				for (var c = 1; c <= options.cols; c++) {
					var sid = r+"_"+c; //座位ID
					var $td = $('<td data-row="'+r+'" data-col="'+c+'" id="'+sid+'"></td>');
					var $seat = $('<span class="seat"></span>');
					$seat.css({
						"width" : options.size + 'px',
						"height" : options.size + 'px'
					});
					try {   //设置边框
						$td.data('thre_id', seats[sid].thre_id);
						$td.data('hall_id', seats[sid].hall_id);
						$td.data('movie_id', seats[sid].movie_id);
						$td.data("price", seats[sid].price);
						//更改座位边框颜色
						if(seats[sid].color) {
							$seat.css({"border-color":seats[sid].color});
							$td.data("color", seats[sid].color);
						}
					} catch (e) {}

					//绑定选座事件
					$td.on("click", function() {
						$(this).find('span').toggleClass("selected");
						var row = $(this).data('row'), col = $(this).data('col');
						var key = row+"_"+col;

						//根据不同的步骤执行不同的操作
						switch (options.step) {

							case 1: //排座
								if ($(this).hasSelected()) {
									seats[key] = {
										"row" : row,
										"col" : col,
										"price" : 0,
										"color" : "",
										"thre_id" : options.thre_id, //剧场ID
										"hall_id" : options.hall_id, //厅ID
										"movie_id" : options.hall_id, //电影场次ID
									};
								} else {
									delete seats[key];
								}
								break;

							case 2:  //设置票价
								if ($(this).hasSelected()) {
									seatColors[key] = 1;
								} else {
									delete seatColors[key];
								}
								console.log(seatColors);
								break;

							case 3: //购票
								var seat = {
									row : $(this).data("row"),
									col : $(this).data("col"),
									thre_id : $(this).data("thre_id"),
									hall_id : $(this).data("hall_id"),
									movie_id : $(this).data("movie_id"),
									price : $(this).data("price"),
								};
								//出发选中事件
								if ($(this).hasSelected()) {
									if (ticketNumber >= options.maxTicketNumber) {
										$(this).unselectSeat(); //选座失败,状态回滚
										options.onerror("一次最多选择 "+options.maxTicketNumber+" 个座位");
										return;
									} else {//选座成功
										options.selected[key] = 1;
										ticketNumber++;
										options.onSelected(seat);
									}
								} else {
									o.unselect(row, col);
									options.onUnselected(seat);
								}
								break;
						}
					}); //end onclick

					//隐藏没有座位的位置
					if (options.step != 1 && seats[sid] == undefined) {
						$seat.css({
							"border" : "none",
							"background" : "none"
						});
						$td.off("click"); //卸载事件
					}

					//处理已选中座位
					if (options.step ==3 && options.selected && options.selected[sid]) {
						$td.off("click"); //卸载事件
						$seat.addClass("disabled");
					}

					$td.append($seat);
					$tr.append($td);

					//如果是排作为，则默认选择全部座位
					if (options.step ==1) {
						$td.trigger("click");
					}
				}
				$table.append($tr);
			}

			//设置电影厅的宽度
			$(options.box).css({
				width : (options.cols * (options.size + 1 + 4)) + "px"
			});
			$(options.box).append($table);
		}

		//获取所有的座位信息
		o.getSeats = function() {
			return seats;
		}

		/**
		 * 设置价格
		 * @param price
		 * @param color
		 */
		o.setPrice = function(price, color) {
			if ( seatColors.length == 0 ) {
				return alert("你没有选中任何座位");
			}
			for (var key in seatColors) {
				seats[key].color = color;
				seats[key].price = price;
				//更改座位边框颜色
				$('#'+key).find("span").css({"border-color": color}).removeClass("selected");
			}
			seatColors = {}; //本次设置完毕，刷新缓存
		}

		//获取已选择的座位
		o.getSelectedSeats = function() {
			return options.selected;
		}

		//释放某个座位
		o.unselect = function(row, col) {
			var key = row+"_"+col;
			delete options.selected[key];
			$("#"+key).unselectSeat();
			ticketNumber--;
		}

		//获取对象元素个数
		function getObjLength(o) {
			var c = 0;
			for (var k in o) {
				c++;
			}
			return c;
		}

		create();
		return o;
	}

	$.seats = function(options) {
		return new __seats__(options);
	};
})(jQuery);