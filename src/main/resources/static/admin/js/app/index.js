/**
 * 公共js文件
 * @author yangjian
 */
"use strict";
define(function(require, exports) {

    var common = require("common");
    var AjaxProxy = require("AjaxProxy");

    $("#cAdd").validator({

        validate: function(validity) {

            // Ajax 验证
            if ($(validity.field).is('.js-ajax-validate')) {
                // 异步操作必须返回 Deferred 对象
                return $.ajax({
                    url: $(validity.field).data("url"),
                    cache: false,
                    dataType: 'json'
                }).then(function(data) {
                    console.log(data);
                    if (data.code != "000") {
                        validity.valid = false;
                    } else {
                        validity.valid = true;
                    }
                    return validity;
                }, function() {
                    validity.valid = false;
                    return validity;
                });
            }

        },

        onValid: function(validity) {
            $(validity.field).closest('.am-form-group').find('.am-alert').hide();
        },

        onInValid: function(validity) {
            var $field = $(validity.field);
            var $group = $field.closest('.am-form-group');
            var $alert = $group.find('.am-alert');
            // 使用自定义的提示信息 或 插件内置的提示信息
            var msg = $field.data('validationMessage') || this.getValidationMessage(validity);

            if (!$alert.length) {
                $alert = $('<div class="am-alert am-alert-danger"></div>').hide().
                appendTo($group);
            }

            $alert.html(msg).show();
        },

        /**
         * form submit handler
         * @returns {boolean}
         */
        submit : function () {

            var formData = $("#cAdd").serialize();
            console.log(formData);
            return false;
        }
    });

    /**
     *  Ajax 代理调用插件
     */
    $(".ajaxproxy").AjaxProxy({
        dataType : "json",
        callBack : function (data) {
            var skin = "red";
            if ( data.code == "000" ) {
                skin = "green";
            }
            layer.msg(data.message,{offset:common.global.layer.msg.offset, skin:"layui-layer-"+skin});
        }
    });

    var vm = new Vue({
        el: '#example',
        data: {
            username : "数据双向绑定"
        }
    })


});
