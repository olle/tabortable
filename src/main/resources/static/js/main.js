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

  /* Triggering initial toggle on load, will reset the mode based on checkbox
     state - which is handled differently by different browsers. */
  _toggleDayNight();

})(window);
