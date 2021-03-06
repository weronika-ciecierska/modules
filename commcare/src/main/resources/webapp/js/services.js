(function () {
    'use strict';

    /* Services */

    var services = angular.module('commcare.services', ['ngResource']);

    services.factory('Configurations', function($resource) {
        return $resource('../commcare/configs/:name', {}, {
            create: {
                method: 'GET',
                url: '../commcare/configs/new'
            },
            makeDefault: {
                method: 'POST',
                url: '../commcare/configs/default'
            },
            verify: {
                method: 'POST',
                url: '../commcare/configs/verify'
            },
            getBaseEndpointUrl: {
                method: 'GET',
                url: '../commcare/configs/endpointBaseUrl'
            }}
        );
    });

    services.factory('Schema', function($resource) {
        return $resource('../commcare/schema/:name', {}, {
            get: {
                method: 'GET',
                isArray: true
            }
        });
    });

    services.factory('Cases', function($resource) {
        return $resource('../commcare/caseList/:name');
    });

}());
