layui.extend({
    aloha: 'lay/modules/aloha',
    validate: 'lay/modules/validate'
}).define(['aloha', 'conf'], function (exports) {
    layui.aloha.initPage();
    exports('index', {});
});