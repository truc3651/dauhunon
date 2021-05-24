mobiscroll.settings = {
  lang: 'en', // Specify language like: lang: 'pl' or omit setting to use default
  theme: 'ios', // Specify theme like: theme: 'ios' or omit setting to use default
  themeVariant: 'light' // More info about themeVariant: https://docs.mobiscroll.com/4-10-9/select#opt-themeVariant
};

$(function() {
  var remoteData = {
      url: 'http://localhost/api/brands',
      type: 'json'
  };

  // Mobiscroll Select initialization
  $('#demo-country-filter-desktop').mobiscroll().select({
      display: 'bubble', // Specify display mode like: display: 'bottom' or omit setting to use default
      touchUi: false, // More info about touchUi: https://docs.mobiscroll.com/4-10-9/select#opt-touchUi
      data: remoteData, // More info about data: https://docs.mobiscroll.com/4-10-9/select#opt-data
      filter: true, // More info about filter: https://docs.mobiscroll.com/4-10-9/select#opt-filter
      group: { // More info about group: https://docs.mobiscroll.com/4-10-9/select#opt-group
          groupWheel: false,
          header: false
      },
      width: 400, // More info about width: https://docs.mobiscroll.com/4-10-9/select#opt-width
      placeholder: 'Please Select...' // More info about placeholder: https://docs.mobiscroll.com/4-10-9/select#opt-placeholder
  });

  // Mobiscroll Select initialization
  $('#demo-country-group-desktop').mobiscroll().select({
      display: 'bubble', // Specify display mode like: display: 'bottom' or omit setting to use default
      touchUi: false, // More info about touchUi: https://docs.mobiscroll.com/4-10-9/select#opt-touchUi
      data: remoteData, // More info about data: https://docs.mobiscroll.com/4-10-9/select#opt-data
      group: true, // More info about group: https://docs.mobiscroll.com/4-10-9/select#opt-group
      placeholder: 'Please Select...' // More info about placeholder: https://docs.mobiscroll.com/4-10-9/select#opt-placeholder
  });
});