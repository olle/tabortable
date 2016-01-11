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

  /* Automatically set day/night checkbox based on hour of day. */
  (function (hour) {
    $global.document.querySelector('input.tgl').checked = hour < 5 || hour > 17;
  })(new Date().getHours());

  /* Triggering initial toggle on load, will reset the mode based on checkbox
     state - which is handled differently by different browsers. */
  _toggleDayNight();

})(window);
