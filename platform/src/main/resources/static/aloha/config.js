layui.define(function(exports) {
  exports('conf', {
    container: 'aloha',
    containerBody: 'aloha-body',
    v: '2.0',
    base: layui.cache.base,
    css: layui.cache.base + 'css/',
    views: '/views',
    viewLoadBar: true,
    debug: layui.cache.debug,
    name: 'aloha',
    entry: '/index',
    engine: '',
    eventName: 'aloha-event',
    tableName: 'aloha',
    requestUrl: './'
  })
});
