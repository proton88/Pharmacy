(function($) {
    $.fn.dropDownBlock = function(block, options) {
        var defaults = {
                speed: 'fast',
                top: $(this).height(),
                left: 0
            },
            opts 	= $.extend(defaults, options),
            toggler = $(this),
            block 	= $(block);
        toggler.css({'outline': 'none'})

        toggler.click(function(e) {
            e.preventDefault();
            $(block).css({
                'position' 	: 'absolute',
                'top' 		: (toggler.offset().top + opts['top']) + 'px',
                'left' 		: (toggler.offset().left + opts['left']) + 'px'
            });
            if($(block).is(':visible')) $(block).fadeOut(opts['speed']);
            else $(block).fadeIn(opts['speed']);
            this.focus();
        });

    };
})(jQuery);
$('#link').dropDownBlock($('#block'));
