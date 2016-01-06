(function($global) {
  'use strict';

  var _toggleDayNight = function() {
    var checked = $global.document.querySelector('input.tgl').checked;
    $global.document.querySelector('body').setAttribute('class', checked ? 'night' : 'day');
  };

  // Public API
  $global.tabortable = {
    toggleDayNight: _toggleDayNight
  };

})(window);
