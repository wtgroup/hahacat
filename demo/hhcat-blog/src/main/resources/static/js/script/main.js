//  js/script/main.js
require(['../require.config'],function () {
    require(['jquery'], function ($) {
        $(document).on('click', '#contentBtn', function () {
            $('#messagebox').html('You have access Jquery by using require()');
        });
    });

})
