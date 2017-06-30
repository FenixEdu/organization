'use strict';

var Utils = {};

Utils.checkI18NCompleteString = function checkI18NCompleteString(supportedLocales, i18n) {
	var result = '';
	for (var i = 0; i < supportedLocales.length; i++) {
		var locale = supportedLocales[i].substring(0, 2);
		var localName = i18n[locale];
		if (localName == null) {
			result += '<span style="color:#ffcc00;" class="glyphicon glyphicon-flag">' + locale + '</span>';
		}
	}
    return result;
};
