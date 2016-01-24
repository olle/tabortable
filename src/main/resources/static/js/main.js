(function($global) {
  'use strict';

  var _toggleDayNight = function() {
    var checked = $global.document.querySelector('input.tgl').checked;
    $global.document.querySelector('body').setAttribute('class', checked ? 'night' : 'day');
  };

  var _adaptTableFontSize = function () {
    var $table = $global.document.querySelector('table');
    var cols = $table.getAttribute('data-columns') || 0;

    var colsClass = '';
    if (cols > 22) {
      colsClass = 'xxl';
    } else if (cols > 14) {
      colsClass = 'xl';
    } else if (cols > 10) {
      colsClass = 'l';
    } else if (cols > 5) {
      colsClass = 'm';
    }
    
    $table.className = ($table.className + ' ' + colsClass).trim();
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

  /* Triggering adapt table font size on load too. */
  _adaptTableFontSize();

})(window);
