(function () {
    'use strict';
    var widgetFunction = function () {
        angular.module('sailpoint.home.desktop.app').directive('spVaadinPluginWidgetWidget', function () {
            return {
                restrict: 'E',
                scope:
                    {
                        widget: '=spWidget'
                    },
                template: ''
                    + '<div>'
				    //+ '<style> html { font-size: 12px; } </style>'
					+ '<script src="sp-vaadin/VAADIN/build/webcomponentsjs/webcomponents-loader.js"></script>'
				    + '<script src="sp-vaadin/web-component/sp-vaadin-widget.js"></script>'
					+ '<sp-vaadin-widget style="width: 100%;"></sp-vaadin-widget>'
					+ '</div>'
            }

        });
    };
    PluginHelper.addWidgetFunction(widgetFunction);
})();

