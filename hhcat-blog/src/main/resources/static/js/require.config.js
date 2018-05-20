// define(function () { //可不要
    require.config(
        {
            //开发时防止浏览器缓存, 上线后固定住 v=v1.0
            urlArgs: 'v='+new Date().getTime(),
            baseUrl: (typeof _contextPath === 'undefined' ? './':_contextPath+'/')+'js/',
            // baseUrl: './js/',
            paths: {
                'jquery': 'lib/jquery-1.8.0.min',
                'bootstrap':'lib/bootstrap.min',
                'zoomify':'lib/zoomify.min',
                'jquery.serialize':'tools/jquery.serialize',
            }
        }
    );
// })