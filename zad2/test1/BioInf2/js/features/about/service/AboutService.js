/**
 *  Defines the AboutService
 *
 *  @author  aborowski
 *  @date    Jan 20, 2016
 *
 */
'use strict';
var AboutService = function($http, utils) {

    this.getDemoList = function() {
        return $http.get(utils.getApi('/demolist'));
    };

};

AboutService.$inject = ['$http', 'utils'];

export default AboutService;
