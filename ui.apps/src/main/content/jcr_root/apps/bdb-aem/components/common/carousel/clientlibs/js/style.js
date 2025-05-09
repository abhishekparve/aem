/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, { enumerable: true, get: getter });
/******/ 		}
/******/ 	};
/******/
/******/ 	// define __esModule on exports
/******/ 	__webpack_require__.r = function(exports) {
/******/ 		if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 			Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 		}
/******/ 		Object.defineProperty(exports, '__esModule', { value: true });
/******/ 	};
/******/
/******/ 	// create a fake namespace object
/******/ 	// mode & 1: value is a module id, require it
/******/ 	// mode & 2: merge all properties of value into the ns
/******/ 	// mode & 4: return value when already ns object
/******/ 	// mode & 8|1: behave like require
/******/ 	__webpack_require__.t = function(value, mode) {
/******/ 		if(mode & 1) value = __webpack_require__(value);
/******/ 		if(mode & 8) return value;
/******/ 		if((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
/******/ 		var ns = Object.create(null);
/******/ 		__webpack_require__.r(ns);
/******/ 		Object.defineProperty(ns, 'default', { enumerable: true, value: value });
/******/ 		if(mode & 2 && typeof value != 'string') for(var key in value) __webpack_require__.d(ns, key, function(key) { return value[key]; }.bind(null, key));
/******/ 		return ns;
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "./";
/******/
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 3);
/******/ })
/************************************************************************/
/******/ ({

/***/ "./node_modules/@brightcove/player-loader/dist/brightcove-player-loader.es.js":
/*!************************************************************************************!*\
  !*** ./node_modules/@brightcove/player-loader/dist/brightcove-player-loader.es.js ***!
  \************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

    "use strict";
    __webpack_require__.r(__webpack_exports__);
    /* harmony import */ var global_document__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! global/document */ "./node_modules/global/document.js");
    /* harmony import */ var global_document__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(global_document__WEBPACK_IMPORTED_MODULE_0__);
    /* harmony import */ var global_window__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! global/window */ "./node_modules/global/window.js");
    /* harmony import */ var global_window__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(global_window__WEBPACK_IMPORTED_MODULE_1__);
    /*! @name @brightcove/player-loader @version 1.8.0 @license Apache-2.0 */
    
    
    
    function _extends() {
      _extends = Object.assign || function (target) {
        for (var i = 1; i < arguments.length; i++) {
          var source = arguments[i];
    
          for (var key in source) {
            if (Object.prototype.hasOwnProperty.call(source, key)) {
              target[key] = source[key];
            }
          }
        }
    
        return target;
      };
    
      return _extends.apply(this, arguments);
    }
    
    var version = "1.8.0";
    
    /*! @name @brightcove/player-url @version 1.2.0 @license Apache-2.0 */
    var version$1 = "1.2.0";
    
    var _typeof = typeof Symbol === "function" && typeof Symbol.iterator === "symbol" ? function (obj) {
      return typeof obj;
    } : function (obj) {
      return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj;
    };
    
    // The parameters that may include JSON.
    var JSON_ALLOWED_PARAMS = ['catalogSearch', 'catalogSequence'];
    
    // The parameters that may be set as query string parameters for iframes.
    var IFRAME_ALLOWED_QUERY_PARAMS = ['adConfigId', 'applicationId', 'catalogSearch', 'catalogSequence', 'playlistId', 'playlistVideoId', 'videoId'];
    
    /**
     * Gets the value of a parameter and encodes it as a string.
     *
     * For certain keys, JSON is allowed and will be encoded.
     *
     * @private
     * @param   {Object} params
     *          A parameters object. See README for details.
     *
     * @param   {string} key
     *          The key in the params object.
     *
     * @return  {string|undefined}
     *          The encoded value - or `undefined` if none.
     */
    var getQueryParamValue = function getQueryParamValue(params, key) {
    
      if (!params || params[key] === undefined) {
        return;
      }
    
      // If it's not a string, such as with a catalog search or sequence, we
      // try to encode it as JSON.
      if (typeof params[key] !== 'string' && JSON_ALLOWED_PARAMS.indexOf(key) !== -1) {
        try {
          return encodeURIComponent(JSON.stringify(params[key]));
        } catch (x) {
    
          // If it's not a string and we can't encode as JSON, it's ignored entirely.
          return;
        }
      }
    
      return encodeURIComponent(String(params[key]).trim()) || undefined;
    };
    
    /**
     * In some cases, we need to add query string parameters to an iframe URL.
     *
     * @private
     * @param   {Object} params
     *          An object of query parameters.
     *
     * @return  {string}
     *          A query string starting with `?`. If no valid parameters are given,
     *          returns an empty string.
     */
    var getQueryString = function getQueryString(params) {
      return Object.keys(params).filter(function (k) {
        return IFRAME_ALLOWED_QUERY_PARAMS.indexOf(k) !== -1;
      }).reduce(function (qs, k) {
        var value = getQueryParamValue(params, k);
    
        if (value !== undefined) {
          qs += qs ? '&' : '?';
          qs += encodeURIComponent(k) + '=' + value;
        }
    
        return qs;
      }, '');
    };
    
    /**
     * Generate a URL to a Brightcove Player.
     *
     * @param  {Object}  params
     *         A set of parameters describing the player URL to create.
     *
     * @param  {string}  params.accountId
     *         A Brightcove account ID.
     *
     * @param  {string}  [params.playerId="default"]
     *         A Brightcove player ID.
     *
     * @param  {string}  [params.embedId="default"]
     *         A Brightcove player embed ID.
     *
     * @param  {boolean} [params.iframe=false]
     *         Whether to return a URL for an HTML document to be embedded in
     *         an iframe.
     *
     * @param  {boolean} [params.minified=true]
     *         When the `iframe` argument is `false`, this can be used to control
     *         whether the minified or unminified JavaScript URL is returned.
     *
     * @param  {string} [params.base="https://players.brightcove.net"]
     *         A base CDN protocol and hostname. Mainly used for testing.
     *
     * @return {string}
     *         A URL to a Brightcove Player.
     */
    var brightcovePlayerUrl = function brightcovePlayerUrl(_ref) {
      var accountId = _ref.accountId,
          _ref$base = _ref.base,
          base = _ref$base === undefined ? 'https://players.brightcove.net' : _ref$base,
          _ref$playerId = _ref.playerId,
          playerId = _ref$playerId === undefined ? 'default' : _ref$playerId,
          _ref$embedId = _ref.embedId,
          embedId = _ref$embedId === undefined ? 'default' : _ref$embedId,
          _ref$iframe = _ref.iframe,
          iframe = _ref$iframe === undefined ? false : _ref$iframe,
          _ref$minified = _ref.minified,
          minified = _ref$minified === undefined ? true : _ref$minified,
          _ref$queryParams = _ref.queryParams,
          queryParams = _ref$queryParams === undefined ? null : _ref$queryParams;
    
      var ext = '';
    
      if (iframe) {
        ext += 'html';
      } else {
        if (minified) {
          ext += 'min.';
        }
        ext += 'js';
      }
    
      if (base.charAt(base.length - 1) === '/') {
        base = base.substring(0, base.length - 1);
      }
    
      var qs = '';
    
      if (iframe && queryParams && (typeof queryParams === 'undefined' ? 'undefined' : _typeof(queryParams)) === 'object') {
        qs = getQueryString(queryParams);
      }
    
      accountId = encodeURIComponent(accountId);
      playerId = encodeURIComponent(playerId);
      embedId = encodeURIComponent(embedId);
    
      return base + '/' + accountId + '/' + playerId + '_' + embedId + '/index.' + ext + qs;
    };
    
    /**
     * The version of this module.
     *
     * @type {string}
     */
    brightcovePlayerUrl.VERSION = version$1;
    
    var DEFAULTS = {
      embedId: 'default',
      embedType: 'in-page',
      playerId: 'default',
      Promise: global_window__WEBPACK_IMPORTED_MODULE_1___default.a.Promise,
      refNodeInsert: 'append'
    };
    var DEFAULT_ASPECT_RATIO = '16:9';
    var DEFAULT_IFRAME_HORIZONTAL_PLAYLIST = false;
    var DEFAULT_MAX_WIDTH = '100%';
    var EMBED_TAG_NAME_VIDEO = 'video';
    var EMBED_TAG_NAME_VIDEOJS = 'video-js';
    var EMBED_TYPE_IN_PAGE = 'in-page';
    var EMBED_TYPE_IFRAME = 'iframe';
    var REF_NODE_INSERT_APPEND = 'append';
    var REF_NODE_INSERT_PREPEND = 'prepend';
    var REF_NODE_INSERT_BEFORE = 'before';
    var REF_NODE_INSERT_AFTER = 'after';
    var REF_NODE_INSERT_REPLACE = 'replace';
    var JSON_ALLOWED_ATTRS = ['catalogSearch', 'catalogSequence'];
    
    var BASE_URL = 'https://players.brightcove.net/';
    /**
     * Gets the URL to a player on CDN.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @return {string}
     *         A URL.
     */
    
    var getUrl = function getUrl(params) {
      if (params.playerUrl) {
        return params.playerUrl;
      }
    
      var accountId = params.accountId,
          playerId = params.playerId,
          embedId = params.embedId,
          embedOptions = params.embedOptions;
      var iframe = params.embedType === EMBED_TYPE_IFRAME;
      return brightcovePlayerUrl({
        accountId: accountId,
        playerId: playerId,
        embedId: embedId,
        iframe: iframe,
        base: BASE_URL,
        // The unminified embed option is the exact reverse of the minified option
        // here.
        minified: embedOptions ? !embedOptions.unminified : true,
        // Pass the entire params object as query params. This is safe because
        // @brightcove/player-url only accepts a whitelist of parameters. Anything
        // else will be ignored.
        queryParams: params
      });
    };
    /**
     * Function used to get the base URL - primarily for testing.
     *
     * @private
     * @return {string}
     *         The current base URL.
     */
    
    
    var getBaseUrl = function getBaseUrl() {
      return BASE_URL;
    };
    /**
     * Function used to set the base URL - primarily for testing.
     *
     * @private
     * @param {string} baseUrl
     *        A new base URL (instead of Brightcove CDN).
     */
    
    
    var setBaseUrl = function setBaseUrl(baseUrl) {
      BASE_URL = baseUrl;
    };
    
    var urls = {
      getUrl: getUrl,
      getBaseUrl: getBaseUrl,
      setBaseUrl: setBaseUrl
    };
    
    /**
     * Is this value an element?
     *
     * @param  {Element} el
     *         A maybe element.
     *
     * @return {boolean}
     *         Whether or not the value is a element.
     */
    
    var isEl = function isEl(el) {
      return Boolean(el && el.nodeType === 1);
    };
    /**
     * Is this value an element with a parent node?
     *
     * @param  {Element} el
     *         A maybe element.
     *
     * @return {boolean}
     *         Whether or not the value is a element with a parent node.
     */
    
    
    var isElInDom = function isElInDom(el) {
      return Boolean(isEl(el) && el.parentNode);
    };
    /**
     * Creates an iframe embed code.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @return {Element}
     *         The DOM element that will ultimately be passed to the `bc()` function.
     */
    
    
    var createIframeEmbed = function createIframeEmbed(params) {
      var el = global_document__WEBPACK_IMPORTED_MODULE_0___default.a.createElement('iframe');
      el.setAttribute('allow', 'autoplay;encrypted-media;fullscreen');
      el.setAttribute('allowfullscreen', 'allowfullscreen');
      el.src = urls.getUrl(params);
      return el;
    };
    /**
     * Creates an in-page embed code.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @return {Element}
     *         The DOM element that will ultimately be passed to the `bc()` function.
     */
    
    
    var createInPageEmbed = function createInPageEmbed(params) {
      var embedOptions = params.embedOptions; // We DO NOT include the data-account, data-player, or data-embed attributes
      // here because we will be manually initializing the player.
    
      var paramsToAttrs = {
        adConfigId: 'data-ad-config-id',
        applicationId: 'data-application-id',
        catalogSearch: 'data-catalog-search',
        catalogSequence: 'data-catalog-sequence',
        deliveryConfigId: 'data-delivery-config-id',
        playlistId: 'data-playlist-id',
        playlistVideoId: 'data-playlist-video-id',
        poster: 'poster',
        videoId: 'data-video-id'
      };
      var tagName = embedOptions && embedOptions.tagName || EMBED_TAG_NAME_VIDEOJS;
      var el = global_document__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(tagName);
      Object.keys(paramsToAttrs).filter(function (key) {
        return params[key];
      }).forEach(function (key) {
        var value; // If it's not a string, such as with a catalog search or sequence, we
        // try to encode it as JSON.
    
        if (typeof params[key] !== 'string' && JSON_ALLOWED_ATTRS.indexOf(key) !== -1) {
          try {
            value = JSON.stringify(params[key]); // If it fails, don't set anything.
          } catch (x) {
            return;
          }
        } else {
          value = String(params[key]).trim();
        }
    
        el.setAttribute(paramsToAttrs[key], value);
      });
      el.setAttribute('controls', 'controls');
      el.classList.add('video-js');
      return el;
    };
    /**
     * Wraps an element in responsive intrinsic ratio elements.
     *
     * @private
     * @param  {string} embedType
     *         The type of the embed.
     *
     * @param  {Object} embedOptions
     *         Embed options from the params.
     *
     * @param  {Element} el
     *         The DOM element.
     *
     * @return {Element}
     *         A new element (if needed).
     */
    
    
    var wrapResponsive = function wrapResponsive(embedType, embedOptions, el) {
      if (!embedOptions.responsive) {
        return el;
      }
    
      el.style.position = 'absolute';
      el.style.top = '0px';
      el.style.right = '0px';
      el.style.bottom = '0px';
      el.style.left = '0px';
      el.style.width = '100%';
      el.style.height = '100%';
    
      var responsive = _extends({
        aspectRatio: DEFAULT_ASPECT_RATIO,
        iframeHorizontalPlaylist: DEFAULT_IFRAME_HORIZONTAL_PLAYLIST,
        maxWidth: DEFAULT_MAX_WIDTH
      }, embedOptions.responsive); // This value is validate at a higher level, so we can trust that it's in the
      // correct format.
    
    
      var aspectRatio = responsive.aspectRatio.split(':').map(Number);
      var inner = global_document__WEBPACK_IMPORTED_MODULE_0___default.a.createElement('div');
      var paddingTop = aspectRatio[1] / aspectRatio[0] * 100; // For iframes with a horizontal playlist, the playlist takes up 20% of the
      // vertical space (if shown); so, adjust the vertical size of the embed to
      // avoid black bars.
    
      if (embedType === EMBED_TYPE_IFRAME && responsive.iframeHorizontalPlaylist) {
        paddingTop *= 1.25;
      }
    
      inner.style.paddingTop = paddingTop + '%';
      inner.appendChild(el);
      var outer = global_document__WEBPACK_IMPORTED_MODULE_0___default.a.createElement('div');
      outer.style.position = 'relative';
      outer.style.display = 'block';
      outer.style.maxWidth = responsive.maxWidth;
      outer.appendChild(inner);
      return outer;
    };
    /**
     * Wraps an element in a Picture-in-Picture plugin container.
     *
     * @private
     * @param  {Object} embedOptions
     *         Embed options from the params.
     *
     * @param  {Element} el
     *         The DOM element.
     *
     * @return {Element}
     *         A new element (if needed).
     */
    
    
    var wrapPip = function wrapPip(embedOptions, el) {
      if (!embedOptions.pip) {
        return el;
      }
    
      var pip = global_document__WEBPACK_IMPORTED_MODULE_0___default.a.createElement('div');
      pip.classList.add('vjs-pip-container');
      pip.appendChild(el);
      return pip;
    };
    /**
     * Wraps a bare embed element with necessary parent elements, depending on
     * embed options given in params.
     *
     * @private
     * @param  {string} embedType
     *         The type of the embed.
     *
     * @param  {Object} embedOptions
     *         Embed options from the params.
     *
     * @param  {Element} embed
     *         The embed DOM element.
     *
     * @return {Element}
     *         A new element (if needed) or the embed itself.
     */
    
    
    var wrapEmbed = function wrapEmbed(embedType, embedOptions, embed) {
      if (!embedOptions) {
        return embed;
      }
    
      return wrapPip(embedOptions, wrapResponsive(embedType, embedOptions, embed));
    };
    /**
     * Inserts a previously-created embed element into the page based on params.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @param  {Element} embed
     *         The embed DOM element.
     *
     * @return {Element}
     *         The embed DOM element.
     */
    
    
    var insertEmbed = function insertEmbed(params, embed) {
      var refNode = params.refNode,
          refNodeInsert = params.refNodeInsert;
      var refNodeParent = refNode.parentNode; // Wrap the embed, if needed, in container elements to support various
      // plugins.
    
      var wrapped = wrapEmbed(params.embedType, params.embedOptions, embed); // Decide where to insert the wrapped embed.
    
      if (refNodeInsert === REF_NODE_INSERT_BEFORE) {
        refNodeParent.insertBefore(wrapped, refNode);
      } else if (refNodeInsert === REF_NODE_INSERT_AFTER) {
        refNodeParent.insertBefore(wrapped, refNode.nextElementSibling || null);
      } else if (refNodeInsert === REF_NODE_INSERT_REPLACE) {
        refNodeParent.replaceChild(wrapped, refNode);
      } else if (refNodeInsert === REF_NODE_INSERT_PREPEND) {
        refNode.insertBefore(wrapped, refNode.firstChild || null); // Append is the default.
      } else {
        refNode.appendChild(wrapped);
      } // If the playlist embed option is provided, we need to add a playlist element
      // immediately after the embed. This has to happen after the embed is inserted
      // into the DOM (above).
    
    
      if (params.embedOptions && params.embedOptions.playlist) {
        var playlistTagName = params.embedOptions.playlist.legacy ? 'ul' : 'div';
        var playlist = global_document__WEBPACK_IMPORTED_MODULE_0___default.a.createElement(playlistTagName);
        playlist.classList.add('vjs-playlist');
        embed.parentNode.insertBefore(playlist, embed.nextElementSibling || null);
      } // Clean up internal reference to the refNode to avoid potential memory
      // leaks in case the params get persisted somewhere. We won't need it beyond
      // this point.
    
    
      params.refNode = null; // Return the original embed element that can be passed to `bc()`.
    
      return embed;
    };
    /**
     * Handles `onEmbedCreated` callback invocation.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @param  {Element} embed
     *         The embed DOM element.
     *
     * @return {Element}
     *         A possibly-new DOM element.
     */
    
    
    var onEmbedCreated = function onEmbedCreated(params, embed) {
      if (typeof params.onEmbedCreated !== 'function') {
        return embed;
      }
    
      var result = params.onEmbedCreated(embed);
    
      if (isEl(result)) {
        return result;
      }
    
      return embed;
    };
    /**
     * Creates an embed code of the appropriate type, runs any customizations
     * necessary, and inserts it into the DOM.
     *
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @return {Element}
     *         The DOM element that will ultimately be passed to the `bc()`
     *         function. Even when customized or wrapped, the return value will be
     *         the target element.
     */
    
    
    var createEmbed = function createEmbed(params) {
      var embed = params.embedType === EMBED_TYPE_IFRAME ? createIframeEmbed(params) : createInPageEmbed(params);
      return insertEmbed(params, onEmbedCreated(params, embed));
    };
    
    //
    // The keys follow the format "accountId_playerId_embedId" where accountId is
    // optional and defaults to "*". This happens when we detect pre-existing
    // player globals.
    
    var actualCache = new global_window__WEBPACK_IMPORTED_MODULE_1___default.a.Map();
    /**
     * Get the cache key given some properties.
     *
     * @private
     * @param  {Object} props
     *         Properties describing the player record to cache.
     *
     * @param  {string} props.playerId
     *         A player ID.
     *
     * @param  {string} props.embedId
     *         An embed ID.
     *
     * @param  {string} [props.accountId="*"]
     *         An optional account ID. This is optional because when we search for
     *         pre-existing players to avoid downloads, we will not necessarily
     *         know the account ID.
     *
     * @return {string}
     *         A key to be used in the script cache.
     */
    
    var key = function key(_ref) {
      var accountId = _ref.accountId,
          playerId = _ref.playerId,
          embedId = _ref.embedId;
      return (accountId || '*') + "_" + playerId + "_" + embedId;
    };
    /**
     * Add an entry to the script cache.
     *
     * @private
     * @param  {Object} props
     *         Properties describing the player record to cache.
     *
     * @param  {string} props.playerId
     *         A player ID.
     *
     * @param  {string} props.embedId
     *         An embed ID.
     *
     * @param  {string} [props.accountId="*"]
     *         An optional account ID. This is optional because when we search for
     *         pre-existing players to avoid downloads, we will not necessarily
     *         know the account ID. If not given, we assume that no script was
     *         downloaded for this player.
     */
    
    
    var store = function store(props) {
      actualCache.set(key(props), props.accountId ? urls.getUrl(props) : '');
    };
    /**
     * Checks if the script cache has an entry.
     *
     * @private
     * @param  {Object} props
     *         Properties describing the player record to cache.
     *
     * @param  {string} props.playerId
     *         A player ID.
     *
     * @param  {string} props.embedId
     *         An embed ID.
     *
     * @param  {string} [props.accountId="*"]
     *         An optional account ID. This is optional because when we search for
     *         pre-existing players to avoid downloads, we will not necessarily
     *         know the account ID.
     *
     * @return {boolean}
     *         Will be `true` if there is a matching cache entry.
     */
    
    
    var has = function has(props) {
      return actualCache.has(key(props));
    };
    /**
     * Gets a cache entry.
     *
     * @private
     * @param  {Object} props
     *         Properties describing the player record to cache.
     *
     * @param  {string} props.playerId
     *         A player ID.
     *
     * @param  {string} props.embedId
     *         An embed ID.
     *
     * @param  {string} [props.accountId="*"]
     *         An optional account ID. This is optional because when we search for
     *         pre-existing players to avoid downloads, we will not necessarily
     *         know the account ID.
     *
     * @return {string}
     *         A cache entry - a URL or empty string.
     *
     */
    
    
    var get = function get(props) {
      return actualCache.get(key(props));
    };
    /**
     * Clears the cache.
     */
    
    
    var clear = function clear() {
      actualCache.clear();
    };
    /**
     * Iterates over the cache.
     *
     * @param  {Function} fn
     *         A callback function that will be called with a value and a key
     *         for each item in the cache.
     */
    
    
    var forEach = function forEach(fn) {
      actualCache.forEach(fn);
    };
    
    var playerScriptCache = {
      clear: clear,
      forEach: forEach,
      get: get,
      has: has,
      key: key,
      store: store
    };
    
    var REGEX_PLAYER_EMBED = /^([A-Za-z0-9]+)_([A-Za-z0-9]+)$/;
    /**
     * Gets an array of current per-player/per-embed `bc` globals that are
     * attached to the `bc` global (e.g. `bc.abc123xyz_default`).
     *
     * If `bc` is not defined, returns an empty array.
     *
     * @private
     * @return {string[]}
     *         An array of keys.
     */
    
    var getBcGlobalKeys = function getBcGlobalKeys() {
      return global_window__WEBPACK_IMPORTED_MODULE_1___default.a.bc ? Object.keys(global_window__WEBPACK_IMPORTED_MODULE_1___default.a.bc).filter(function (k) {
        return REGEX_PLAYER_EMBED.test(k);
      }) : [];
    };
    /**
     * Gets known global object keys that Brightcove Players may create.
     *
     * @private
     * @return {string[]}
     *         An array of global variables that were added during testing.
     */
    
    
    var getGlobalKeys = function getGlobalKeys() {
      return Object.keys(global_window__WEBPACK_IMPORTED_MODULE_1___default.a).filter(function (k) {
        return /^videojs/i.test(k) || /^(bc)$/.test(k);
      });
    };
    /**
     * Dispose all players from a copy of Video.js.
     *
     * @param  {Function} videojs
     *         A copy of Video.js.
     */
    
    
    var disposeAll = function disposeAll(videojs) {
      if (!videojs) {
        return;
      }
    
      Object.keys(videojs.players).forEach(function (k) {
        var p = videojs.players[k];
    
        if (p) {
          p.dispose();
        }
      });
    };
    /**
     * Resets environment state.
     *
     * This will dispose ALL Video.js players on the page and remove ALL `bc` and
     * `videojs` globals it finds.
     */
    
    
    var reset = function reset() {
      // Remove all script elements from the DOM.
      playerScriptCache.forEach(function (value, key) {
        // If no script URL is associated, skip it.
        if (!value) {
          return;
        } // Find all script elements and remove them.
    
    
        Array.prototype.slice.call(global_document__WEBPACK_IMPORTED_MODULE_0___default.a.querySelectorAll("script[src=\"" + value + "\"]")).forEach(function (el) {
          return el.parentNode.removeChild(el);
        });
      }); // Clear the internal cache that have been downloaded.
    
      playerScriptCache.clear(); // Dispose any remaining players from the `videojs` global.
    
      disposeAll(global_window__WEBPACK_IMPORTED_MODULE_1___default.a.videojs); // There may be other `videojs` instances lurking in the bowels of the
      // `bc` global. This should eliminate any of those.
    
      getBcGlobalKeys().forEach(function (k) {
        return disposeAll(global_window__WEBPACK_IMPORTED_MODULE_1___default.a.bc[k].videojs);
      }); // Delete any global object keys that were created.
    
      getGlobalKeys().forEach(function (k) {
        delete global_window__WEBPACK_IMPORTED_MODULE_1___default.a[k];
      });
    };
    /**
     * At runtime, populate the cache with pre-detected players. This allows
     * people who have bundled their player or included a script tag before this
     * runs to not have to re-download players.
     */
    
    
    var detectPlayers = function detectPlayers() {
      getBcGlobalKeys().forEach(function (k) {
        var matches = k.match(REGEX_PLAYER_EMBED);
        var props = {
          playerId: matches[1],
          embedId: matches[2]
        };
    
        if (!playerScriptCache.has(props)) {
          playerScriptCache.store(props);
        }
      });
    };
    
    var env = {
      detectPlayers: detectPlayers,
      reset: reset
    };
    
    env.detectPlayers();
    /**
     * Is this value a function?
     *
     * @private
     * @param  {Function} fn
     *         A maybe function.
     *
     * @return {boolean}
     *         Whether or not the value is a function.
     */
    
    var isFn = function isFn(fn) {
      return typeof fn === 'function';
    };
    /**
     * Checks whether an embedType parameter is valid.
     *
     * @private
     * @param  {string} embedType
     *         The value to test.
     *
     * @return {boolean}
     *         Whether the value is valid.
     */
    
    
    var isValidEmbedType = function isValidEmbedType(embedType) {
      return embedType === EMBED_TYPE_IN_PAGE || embedType === EMBED_TYPE_IFRAME;
    };
    /**
     * Checks whether an embedOptions.tagName parameter is valid.
     *
     * @private
     * @param  {string} tagName
     *         The value to test.
     *
     * @return {boolean}
     *         Whether the value is valid.
     */
    
    
    var isValidTagName = function isValidTagName(tagName) {
      return tagName === EMBED_TAG_NAME_VIDEOJS || tagName === EMBED_TAG_NAME_VIDEO;
    };
    /**
     * Checks whether a refNodeInsert parameter is valid.
     *
     * @private
     * @param  {string} refNodeInsert
     *         The value to test.
     *
     * @return {boolean}
     *         Whether the value is valid.
     */
    
    
    var isValidRootInsert = function isValidRootInsert(refNodeInsert) {
      return refNodeInsert === REF_NODE_INSERT_APPEND || refNodeInsert === REF_NODE_INSERT_PREPEND || refNodeInsert === REF_NODE_INSERT_BEFORE || refNodeInsert === REF_NODE_INSERT_AFTER || refNodeInsert === REF_NODE_INSERT_REPLACE;
    };
    /**
     * Checks parameters and throws an error on validation problems.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @throws {Error} If accountId is missing.
     * @throws {Error} If refNode is missing or invalid.
     * @throws {Error} If embedType is missing or invalid.
     * @throws {Error} If attempting to use an iframe embed with options.
     * @throws {Error} If attempting to use embedOptions.responsiveIframe with a
     *                 non-iframe embed.
     * @throws {Error} If refNodeInsert is missing or invalid.
     */
    
    
    var checkParams = function checkParams(params) {
      var accountId = params.accountId,
          embedOptions = params.embedOptions,
          embedType = params.embedType,
          options = params.options,
          refNode = params.refNode,
          refNodeInsert = params.refNodeInsert;
    
      if (!accountId) {
        throw new Error('accountId is required');
      } else if (!isElInDom(refNode)) {
        throw new Error('refNode must resolve to a node attached to the DOM');
      } else if (!isValidEmbedType(embedType)) {
        throw new Error('embedType is missing or invalid');
      } else if (embedType === EMBED_TYPE_IFRAME && options) {
        throw new Error('cannot use options with an iframe embed');
      } else if (embedOptions && embedOptions.tagName !== undefined && !isValidTagName(embedOptions.tagName)) {
        throw new Error("embedOptions.tagName is invalid (value: \"" + embedOptions.tagName + "\")");
      } else if (embedOptions && embedOptions.responsive && embedOptions.responsive.aspectRatio && !/^\d+\:\d+$/.test(embedOptions.responsive.aspectRatio)) {
        throw new Error("embedOptions.responsive.aspectRatio must be in the \"n:n\" format (value: \"" + embedOptions.responsive.aspectRatio + "\")");
      } else if (!isValidRootInsert(refNodeInsert)) {
        throw new Error('refNodeInsert is missing or invalid');
      }
    };
    /**
     * Normalizes a `refNode` param to an element - or `null`.
     *
     * @private
     * @param  {Element|string} refNode
     *         The value of a `refNode` param.
     *
     * @return {Element|null}
     *         A DOM element or `null` if the `refNode` was given as a string and
     *         did not match an element.
     */
    
    
    var resolveRefNode = function resolveRefNode(refNode) {
      if (isElInDom(refNode)) {
        return refNode;
      }
    
      if (typeof refNode === 'string') {
        return global_document__WEBPACK_IMPORTED_MODULE_0___default.a.querySelector(refNode);
      }
    
      return null;
    };
    /**
     * Initializes a player and returns it.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @param  {Element} embed
     *         An element that will be passed to the `bc()` function.
     *
     * @param  {Function} resolve
     *         A function to call if a player is successfully initialized.
     *
     * @param  {Function} reject
     *         A function to call if a player fails to be initialized.
     *
     * @return {Object}
     *         A success object whose `ref` is a player.
     */
    
    
    var initPlayer = function initPlayer(params, embed, resolve, reject) {
      var embedId = params.embedId,
          playerId = params.playerId;
      var bc = global_window__WEBPACK_IMPORTED_MODULE_1___default.a.bc[playerId + "_" + embedId] || global_window__WEBPACK_IMPORTED_MODULE_1___default.a.bc;
    
      if (!bc) {
        return reject(new Error("missing bc function for " + playerId));
      }
    
      playerScriptCache.store(params);
      var player;
    
      try {
        player = bc(embed, params.options); // Add a PLAYER_LOADER property to bcinfo to indicate this player was
        // loaded via that mechanism.
    
        if (player.bcinfo) {
          player.bcinfo.PLAYER_LOADER = true;
        }
      } catch (x) {
        var message = 'Could not initialize the Brightcove Player.'; // Update the rejection message based on known conditions that can cause it.
    
        if (params.embedOptions.tagName === EMBED_TAG_NAME_VIDEOJS) {
          message += ' You are attempting to embed using a "video-js" element.' + ' Please ensure that your Player is v6.11.0 or newer in order to' + ' support this embed type. Alternatively, pass `"video"` for' + ' `embedOptions.tagName`.';
        }
    
        return reject(new Error(message));
      }
    
      resolve({
        type: EMBED_TYPE_IN_PAGE,
        ref: player
      });
    };
    /**
     * Loads a player from CDN and embeds it.
     *
     * @private
     * @param  {Object} params
     *         A parameters object. See README for details.
     *
     * @param  {Function} resolve
     *         A function to call if a player is successfully initialized.
     *
     * @param  {Function} reject
     *         A function to call if a player fails to be initialized.
     */
    
    
    var loadPlayer = function loadPlayer(params, resolve, reject) {
      params.refNode = resolveRefNode(params.refNode);
      checkParams(params);
      var refNode = params.refNode,
          refNodeInsert = params.refNodeInsert; // Store a reference to the refNode parent. When we use the replace method,
      // we'll need it as the location to store the script element.
    
      var refNodeParent = refNode.parentNode;
      var embed = createEmbed(params); // If this is an iframe, all we need to do is create the embed code and
      // inject it. Because there is no reliable way to hook into an iframe from
      // the parent page, we simply resolve immediately upon creating the embed.
    
      if (params.embedType === EMBED_TYPE_IFRAME) {
        resolve({
          type: EMBED_TYPE_IFRAME,
          ref: embed
        });
        return;
      } // If we've already downloaded this script or detected a matching global, we
      // should have the proper `bc` global and can bypass the script creation
      // process.
    
    
      if (playerScriptCache.has(params)) {
        return initPlayer(params, embed, resolve, reject);
      }
    
      var script = global_document__WEBPACK_IMPORTED_MODULE_0___default.a.createElement('script');
    
      script.onload = function () {
        return initPlayer(params, embed, resolve, reject);
      };
    
      script.onerror = function () {
        reject(new Error('player script could not be downloaded'));
      };
    
      script.async = true;
      script.charset = 'utf-8';
      script.src = urls.getUrl(params);
    
      if (refNodeInsert === REF_NODE_INSERT_REPLACE) {
        refNodeParent.appendChild(script);
      } else {
        refNode.appendChild(script);
      }
    };
    /**
     * A function for asynchronously loading a Brightcove Player into a web page.
     *
     * @param  {Object} parameters
     *         A parameters object. See README for details.
     *
     * @return {Promise|undefined}
     *         A Promise, if possible.
     */
    
    
    var brightcovePlayerLoader = function brightcovePlayerLoader(parameters) {
      var params = _extends({}, DEFAULTS, parameters);
    
      var Promise = params.Promise,
          onSuccess = params.onSuccess,
          onFailure = params.onFailure; // When Promise is not available or any success/failure callback is given,
      // do not attempt to use Promises.
    
      if (!isFn(Promise) || isFn(onSuccess) || isFn(onFailure)) {
        return loadPlayer(params, isFn(onSuccess) ? onSuccess : function () {}, isFn(onFailure) ? onFailure : function (err) {
          throw err;
        });
      } // Promises are supported, use 'em.
    
    
      return new Promise(function (resolve, reject) {
        return loadPlayer(params, resolve, reject);
      });
    };
    /**
     * Expose a non-writable, non-configurable property on the
     * `brightcovePlayerLoader` function.
     *
     * @private
     * @param  {string} key
     *         The property key.
     *
     * @param  {string|Function} value
     *         The value.
     */
    
    
    var expose = function expose(key, value) {
      Object.defineProperty(brightcovePlayerLoader, key, {
        configurable: false,
        enumerable: true,
        value: value,
        writable: false
      });
    };
    /**
     * Get the base URL for players. By default, this will be the Brightcove CDN.
     *
     * @return {string}
     *         The current base URL.
     */
    
    
    expose('getBaseUrl', function () {
      return urls.getBaseUrl();
    });
    /**
     * Set the base URL for players. By default, this will be the Brightcove CDN,
     * but can be overridden with this function.
     *
     * @param {string} baseUrl
     *        A new base URL (instead of Brightcove CDN).
     */
    
    expose('setBaseUrl', function (baseUrl) {
      urls.setBaseUrl(baseUrl);
    });
    /**
     * Get the URL for a player.
     */
    
    expose('getUrl', function (options) {
      return urls.getUrl(options);
    });
    /**
     * Completely resets global state.
     *
     * This will dispose ALL Video.js players on the page and remove ALL `bc` and
     * `videojs` globals it finds.
     */
    
    expose('reset', function () {
      return env.reset();
    }); // Define some read-only constants on the exported function.
    
    [['EMBED_TAG_NAME_VIDEO', EMBED_TAG_NAME_VIDEO], ['EMBED_TAG_NAME_VIDEOJS', EMBED_TAG_NAME_VIDEOJS], ['EMBED_TYPE_IN_PAGE', EMBED_TYPE_IN_PAGE], ['EMBED_TYPE_IFRAME', EMBED_TYPE_IFRAME], ['REF_NODE_INSERT_APPEND', REF_NODE_INSERT_APPEND], ['REF_NODE_INSERT_PREPEND', REF_NODE_INSERT_PREPEND], ['REF_NODE_INSERT_BEFORE', REF_NODE_INSERT_BEFORE], ['REF_NODE_INSERT_AFTER', REF_NODE_INSERT_AFTER], ['REF_NODE_INSERT_REPLACE', REF_NODE_INSERT_REPLACE], ['VERSION', version]].forEach(function (arr) {
      expose(arr[0], arr[1]);
    });
    
    /* harmony default export */ __webpack_exports__["default"] = (brightcovePlayerLoader);
    
    
    /***/ }),
    
    /***/ "./node_modules/bootstrap/js/dist/modal.js":
    /*!*************************************************!*\
      !*** ./node_modules/bootstrap/js/dist/modal.js ***!
      \*************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    /*!
      * Bootstrap modal.js v4.6.0 (https://getbootstrap.com/)
      * Copyright 2011-2021 The Bootstrap Authors (https://github.com/twbs/bootstrap/graphs/contributors)
      * Licensed under MIT (https://github.com/twbs/bootstrap/blob/main/LICENSE)
      */
    (function (global, factory) {
       true ? module.exports = factory(__webpack_require__(/*! jquery */ "./node_modules/jquery/dist/jquery.js"), __webpack_require__(/*! ./util.js */ "./node_modules/bootstrap/js/dist/util.js")) :
      undefined;
    }(this, (function ($, Util) { 'use strict';
    
      function _interopDefaultLegacy (e) { return e && typeof e === 'object' && 'default' in e ? e : { 'default': e }; }
    
      var $__default = /*#__PURE__*/_interopDefaultLegacy($);
      var Util__default = /*#__PURE__*/_interopDefaultLegacy(Util);
    
      function _defineProperties(target, props) {
        for (var i = 0; i < props.length; i++) {
          var descriptor = props[i];
          descriptor.enumerable = descriptor.enumerable || false;
          descriptor.configurable = true;
          if ("value" in descriptor) descriptor.writable = true;
          Object.defineProperty(target, descriptor.key, descriptor);
        }
      }
    
      function _createClass(Constructor, protoProps, staticProps) {
        if (protoProps) _defineProperties(Constructor.prototype, protoProps);
        if (staticProps) _defineProperties(Constructor, staticProps);
        return Constructor;
      }
    
      function _extends() {
        _extends = Object.assign || function (target) {
          for (var i = 1; i < arguments.length; i++) {
            var source = arguments[i];
    
            for (var key in source) {
              if (Object.prototype.hasOwnProperty.call(source, key)) {
                target[key] = source[key];
              }
            }
          }
    
          return target;
        };
    
        return _extends.apply(this, arguments);
      }
    
      /**
       * ------------------------------------------------------------------------
       * Constants
       * ------------------------------------------------------------------------
       */
    
      var NAME = 'modal';
      var VERSION = '4.6.0';
      var DATA_KEY = 'bs.modal';
      var EVENT_KEY = "." + DATA_KEY;
      var DATA_API_KEY = '.data-api';
      var JQUERY_NO_CONFLICT = $__default['default'].fn[NAME];
      var ESCAPE_KEYCODE = 27; // KeyboardEvent.which value for Escape (Esc) key
    
      var Default = {
        backdrop: true,
        keyboard: true,
        focus: true,
        show: true
      };
      var DefaultType = {
        backdrop: '(boolean|string)',
        keyboard: 'boolean',
        focus: 'boolean',
        show: 'boolean'
      };
      var EVENT_HIDE = "hide" + EVENT_KEY;
      var EVENT_HIDE_PREVENTED = "hidePrevented" + EVENT_KEY;
      var EVENT_HIDDEN = "hidden" + EVENT_KEY;
      var EVENT_SHOW = "show" + EVENT_KEY;
      var EVENT_SHOWN = "shown" + EVENT_KEY;
      var EVENT_FOCUSIN = "focusin" + EVENT_KEY;
      var EVENT_RESIZE = "resize" + EVENT_KEY;
      var EVENT_CLICK_DISMISS = "click.dismiss" + EVENT_KEY;
      var EVENT_KEYDOWN_DISMISS = "keydown.dismiss" + EVENT_KEY;
      var EVENT_MOUSEUP_DISMISS = "mouseup.dismiss" + EVENT_KEY;
      var EVENT_MOUSEDOWN_DISMISS = "mousedown.dismiss" + EVENT_KEY;
      var EVENT_CLICK_DATA_API = "click" + EVENT_KEY + DATA_API_KEY;
      var CLASS_NAME_SCROLLABLE = 'modal-dialog-scrollable';
      var CLASS_NAME_SCROLLBAR_MEASURER = 'modal-scrollbar-measure';
      var CLASS_NAME_BACKDROP = 'modal-backdrop';
      var CLASS_NAME_OPEN = 'modal-open';
      var CLASS_NAME_FADE = 'fade';
      var CLASS_NAME_SHOW = 'show';
      var CLASS_NAME_STATIC = 'modal-static';
      var SELECTOR_DIALOG = '.modal-dialog';
      var SELECTOR_MODAL_BODY = '.modal-body';
      var SELECTOR_DATA_TOGGLE = '[data-toggle="modal"]';
      var SELECTOR_DATA_DISMISS = '[data-dismiss="modal"]';
      var SELECTOR_FIXED_CONTENT = '.fixed-top, .fixed-bottom, .is-fixed, .sticky-top';
      var SELECTOR_STICKY_CONTENT = '.sticky-top';
      /**
       * ------------------------------------------------------------------------
       * Class Definition
       * ------------------------------------------------------------------------
       */
    
      var Modal = /*#__PURE__*/function () {
        function Modal(element, config) {
          this._config = this._getConfig(config);
          this._element = element;
          this._dialog = element.querySelector(SELECTOR_DIALOG);
          this._backdrop = null;
          this._isShown = false;
          this._isBodyOverflowing = false;
          this._ignoreBackdropClick = false;
          this._isTransitioning = false;
          this._scrollbarWidth = 0;
        } // Getters
    
    
        var _proto = Modal.prototype;
    
        // Public
        _proto.toggle = function toggle(relatedTarget) {
          return this._isShown ? this.hide() : this.show(relatedTarget);
        };
    
        _proto.show = function show(relatedTarget) {
          var _this = this;
    
          if (this._isShown || this._isTransitioning) {
            return;
          }
    
          if ($__default['default'](this._element).hasClass(CLASS_NAME_FADE)) {
            this._isTransitioning = true;
          }
    
          var showEvent = $__default['default'].Event(EVENT_SHOW, {
            relatedTarget: relatedTarget
          });
          $__default['default'](this._element).trigger(showEvent);
    
          if (this._isShown || showEvent.isDefaultPrevented()) {
            return;
          }
    
          this._isShown = true;
    
          this._checkScrollbar();
    
          this._setScrollbar();
    
          this._adjustDialog();
    
          this._setEscapeEvent();
    
          this._setResizeEvent();
    
          $__default['default'](this._element).on(EVENT_CLICK_DISMISS, SELECTOR_DATA_DISMISS, function (event) {
            return _this.hide(event);
          });
          $__default['default'](this._dialog).on(EVENT_MOUSEDOWN_DISMISS, function () {
            $__default['default'](_this._element).one(EVENT_MOUSEUP_DISMISS, function (event) {
              if ($__default['default'](event.target).is(_this._element)) {
                _this._ignoreBackdropClick = true;
              }
            });
          });
    
          this._showBackdrop(function () {
            return _this._showElement(relatedTarget);
          });
        };
    
        _proto.hide = function hide(event) {
          var _this2 = this;
    
          if (event) {
            event.preventDefault();
          }
    
          if (!this._isShown || this._isTransitioning) {
            return;
          }
    
          var hideEvent = $__default['default'].Event(EVENT_HIDE);
          $__default['default'](this._element).trigger(hideEvent);
    
          if (!this._isShown || hideEvent.isDefaultPrevented()) {
            return;
          }
    
          this._isShown = false;
          var transition = $__default['default'](this._element).hasClass(CLASS_NAME_FADE);
    
          if (transition) {
            this._isTransitioning = true;
          }
    
          this._setEscapeEvent();
    
          this._setResizeEvent();
    
          $__default['default'](document).off(EVENT_FOCUSIN);
          $__default['default'](this._element).removeClass(CLASS_NAME_SHOW);
          $__default['default'](this._element).off(EVENT_CLICK_DISMISS);
          $__default['default'](this._dialog).off(EVENT_MOUSEDOWN_DISMISS);
    
          if (transition) {
            var transitionDuration = Util__default['default'].getTransitionDurationFromElement(this._element);
            $__default['default'](this._element).one(Util__default['default'].TRANSITION_END, function (event) {
              return _this2._hideModal(event);
            }).emulateTransitionEnd(transitionDuration);
          } else {
            this._hideModal();
          }
        };
    
        _proto.dispose = function dispose() {
          [window, this._element, this._dialog].forEach(function (htmlElement) {
            return $__default['default'](htmlElement).off(EVENT_KEY);
          });
          /**
           * `document` has 2 events `EVENT_FOCUSIN` and `EVENT_CLICK_DATA_API`
           * Do not move `document` in `htmlElements` array
           * It will remove `EVENT_CLICK_DATA_API` event that should remain
           */
    
          $__default['default'](document).off(EVENT_FOCUSIN);
          $__default['default'].removeData(this._element, DATA_KEY);
          this._config = null;
          this._element = null;
          this._dialog = null;
          this._backdrop = null;
          this._isShown = null;
          this._isBodyOverflowing = null;
          this._ignoreBackdropClick = null;
          this._isTransitioning = null;
          this._scrollbarWidth = null;
        };
    
        _proto.handleUpdate = function handleUpdate() {
          this._adjustDialog();
        } // Private
        ;
    
        _proto._getConfig = function _getConfig(config) {
          config = _extends({}, Default, config);
          Util__default['default'].typeCheckConfig(NAME, config, DefaultType);
          return config;
        };
    
        _proto._triggerBackdropTransition = function _triggerBackdropTransition() {
          var _this3 = this;
    
          var hideEventPrevented = $__default['default'].Event(EVENT_HIDE_PREVENTED);
          $__default['default'](this._element).trigger(hideEventPrevented);
    
          if (hideEventPrevented.isDefaultPrevented()) {
            return;
          }
    
          var isModalOverflowing = this._element.scrollHeight > document.documentElement.clientHeight;
    
          if (!isModalOverflowing) {
            this._element.style.overflowY = 'hidden';
          }
    
          this._element.classList.add(CLASS_NAME_STATIC);
    
          var modalTransitionDuration = Util__default['default'].getTransitionDurationFromElement(this._dialog);
          $__default['default'](this._element).off(Util__default['default'].TRANSITION_END);
          $__default['default'](this._element).one(Util__default['default'].TRANSITION_END, function () {
            _this3._element.classList.remove(CLASS_NAME_STATIC);
    
            if (!isModalOverflowing) {
              $__default['default'](_this3._element).one(Util__default['default'].TRANSITION_END, function () {
                _this3._element.style.overflowY = '';
              }).emulateTransitionEnd(_this3._element, modalTransitionDuration);
            }
          }).emulateTransitionEnd(modalTransitionDuration);
    
          this._element.focus();
        };
    
        _proto._showElement = function _showElement(relatedTarget) {
          var _this4 = this;
    
          var transition = $__default['default'](this._element).hasClass(CLASS_NAME_FADE);
          var modalBody = this._dialog ? this._dialog.querySelector(SELECTOR_MODAL_BODY) : null;
    
          if (!this._element.parentNode || this._element.parentNode.nodeType !== Node.ELEMENT_NODE) {
            // Don't move modal's DOM position
            document.body.appendChild(this._element);
          }
    
          this._element.style.display = 'block';
    
          this._element.removeAttribute('aria-hidden');
    
          this._element.setAttribute('aria-modal', true);
    
          this._element.setAttribute('role', 'dialog');
    
          if ($__default['default'](this._dialog).hasClass(CLASS_NAME_SCROLLABLE) && modalBody) {
            modalBody.scrollTop = 0;
          } else {
            this._element.scrollTop = 0;
          }
    
          if (transition) {
            Util__default['default'].reflow(this._element);
          }
    
          $__default['default'](this._element).addClass(CLASS_NAME_SHOW);
    
          if (this._config.focus) {
            this._enforceFocus();
          }
    
          var shownEvent = $__default['default'].Event(EVENT_SHOWN, {
            relatedTarget: relatedTarget
          });
    
          var transitionComplete = function transitionComplete() {
            if (_this4._config.focus) {
              _this4._element.focus();
            }
    
            _this4._isTransitioning = false;
            $__default['default'](_this4._element).trigger(shownEvent);
          };
    
          if (transition) {
            var transitionDuration = Util__default['default'].getTransitionDurationFromElement(this._dialog);
            $__default['default'](this._dialog).one(Util__default['default'].TRANSITION_END, transitionComplete).emulateTransitionEnd(transitionDuration);
          } else {
            transitionComplete();
          }
        };
    
        _proto._enforceFocus = function _enforceFocus() {
          var _this5 = this;
    
          $__default['default'](document).off(EVENT_FOCUSIN) // Guard against infinite focus loop
          .on(EVENT_FOCUSIN, function (event) {
            if (document !== event.target && _this5._element !== event.target && $__default['default'](_this5._element).has(event.target).length === 0) {
              _this5._element.focus();
            }
          });
        };
    
        _proto._setEscapeEvent = function _setEscapeEvent() {
          var _this6 = this;
    
          if (this._isShown) {
            $__default['default'](this._element).on(EVENT_KEYDOWN_DISMISS, function (event) {
              if (_this6._config.keyboard && event.which === ESCAPE_KEYCODE) {
                event.preventDefault();
    
                _this6.hide();
              } else if (!_this6._config.keyboard && event.which === ESCAPE_KEYCODE) {
                _this6._triggerBackdropTransition();
              }
            });
          } else if (!this._isShown) {
            $__default['default'](this._element).off(EVENT_KEYDOWN_DISMISS);
          }
        };
    
        _proto._setResizeEvent = function _setResizeEvent() {
          var _this7 = this;
    
          if (this._isShown) {
            $__default['default'](window).on(EVENT_RESIZE, function (event) {
              return _this7.handleUpdate(event);
            });
          } else {
            $__default['default'](window).off(EVENT_RESIZE);
          }
        };
    
        _proto._hideModal = function _hideModal() {
          var _this8 = this;
    
          this._element.style.display = 'none';
    
          this._element.setAttribute('aria-hidden', true);
    
          this._element.removeAttribute('aria-modal');
    
          this._element.removeAttribute('role');
    
          this._isTransitioning = false;
    
          this._showBackdrop(function () {
            $__default['default'](document.body).removeClass(CLASS_NAME_OPEN);
    
            _this8._resetAdjustments();
    
            _this8._resetScrollbar();
    
            $__default['default'](_this8._element).trigger(EVENT_HIDDEN);
          });
        };
    
        _proto._removeBackdrop = function _removeBackdrop() {
          if (this._backdrop) {
            $__default['default'](this._backdrop).remove();
            this._backdrop = null;
          }
        };
    
        _proto._showBackdrop = function _showBackdrop(callback) {
          var _this9 = this;
    
          var animate = $__default['default'](this._element).hasClass(CLASS_NAME_FADE) ? CLASS_NAME_FADE : '';
    
          if (this._isShown && this._config.backdrop) {
            this._backdrop = document.createElement('div');
            this._backdrop.className = CLASS_NAME_BACKDROP;
    
            if (animate) {
              this._backdrop.classList.add(animate);
            }
    
            $__default['default'](this._backdrop).appendTo(document.body);
            $__default['default'](this._element).on(EVENT_CLICK_DISMISS, function (event) {
              if (_this9._ignoreBackdropClick) {
                _this9._ignoreBackdropClick = false;
                return;
              }
    
              if (event.target !== event.currentTarget) {
                return;
              }
    
              if (_this9._config.backdrop === 'static') {
                _this9._triggerBackdropTransition();
              } else {
                _this9.hide();
              }
            });
    
            if (animate) {
              Util__default['default'].reflow(this._backdrop);
            }
    
            $__default['default'](this._backdrop).addClass(CLASS_NAME_SHOW);
    
            if (!callback) {
              return;
            }
    
            if (!animate) {
              callback();
              return;
            }
    
            var backdropTransitionDuration = Util__default['default'].getTransitionDurationFromElement(this._backdrop);
            $__default['default'](this._backdrop).one(Util__default['default'].TRANSITION_END, callback).emulateTransitionEnd(backdropTransitionDuration);
          } else if (!this._isShown && this._backdrop) {
            $__default['default'](this._backdrop).removeClass(CLASS_NAME_SHOW);
    
            var callbackRemove = function callbackRemove() {
              _this9._removeBackdrop();
    
              if (callback) {
                callback();
              }
            };
    
            if ($__default['default'](this._element).hasClass(CLASS_NAME_FADE)) {
              var _backdropTransitionDuration = Util__default['default'].getTransitionDurationFromElement(this._backdrop);
    
              $__default['default'](this._backdrop).one(Util__default['default'].TRANSITION_END, callbackRemove).emulateTransitionEnd(_backdropTransitionDuration);
            } else {
              callbackRemove();
            }
          } else if (callback) {
            callback();
          }
        } // ----------------------------------------------------------------------
        // the following methods are used to handle overflowing modals
        // todo (fat): these should probably be refactored out of modal.js
        // ----------------------------------------------------------------------
        ;
    
        _proto._adjustDialog = function _adjustDialog() {
          var isModalOverflowing = this._element.scrollHeight > document.documentElement.clientHeight;
    
          if (!this._isBodyOverflowing && isModalOverflowing) {
            this._element.style.paddingLeft = this._scrollbarWidth + "px";
          }
    
          if (this._isBodyOverflowing && !isModalOverflowing) {
            this._element.style.paddingRight = this._scrollbarWidth + "px";
          }
        };
    
        _proto._resetAdjustments = function _resetAdjustments() {
          this._element.style.paddingLeft = '';
          this._element.style.paddingRight = '';
        };
    
        _proto._checkScrollbar = function _checkScrollbar() {
          var rect = document.body.getBoundingClientRect();
          this._isBodyOverflowing = Math.round(rect.left + rect.right) < window.innerWidth;
          this._scrollbarWidth = this._getScrollbarWidth();
        };
    
        _proto._setScrollbar = function _setScrollbar() {
          var _this10 = this;
    
          if (this._isBodyOverflowing) {
            // Note: DOMNode.style.paddingRight returns the actual value or '' if not set
            //   while $(DOMNode).css('padding-right') returns the calculated value or 0 if not set
            var fixedContent = [].slice.call(document.querySelectorAll(SELECTOR_FIXED_CONTENT));
            var stickyContent = [].slice.call(document.querySelectorAll(SELECTOR_STICKY_CONTENT)); // Adjust fixed content padding
    
            $__default['default'](fixedContent).each(function (index, element) {
              var actualPadding = element.style.paddingRight;
              var calculatedPadding = $__default['default'](element).css('padding-right');
              $__default['default'](element).data('padding-right', actualPadding).css('padding-right', parseFloat(calculatedPadding) + _this10._scrollbarWidth + "px");
            }); // Adjust sticky content margin
    
            $__default['default'](stickyContent).each(function (index, element) {
              var actualMargin = element.style.marginRight;
              var calculatedMargin = $__default['default'](element).css('margin-right');
              $__default['default'](element).data('margin-right', actualMargin).css('margin-right', parseFloat(calculatedMargin) - _this10._scrollbarWidth + "px");
            }); // Adjust body padding
    
            var actualPadding = document.body.style.paddingRight;
            var calculatedPadding = $__default['default'](document.body).css('padding-right');
            $__default['default'](document.body).data('padding-right', actualPadding).css('padding-right', parseFloat(calculatedPadding) + this._scrollbarWidth + "px");
          }
    
          $__default['default'](document.body).addClass(CLASS_NAME_OPEN);
        };
    
        _proto._resetScrollbar = function _resetScrollbar() {
          // Restore fixed content padding
          var fixedContent = [].slice.call(document.querySelectorAll(SELECTOR_FIXED_CONTENT));
          $__default['default'](fixedContent).each(function (index, element) {
            var padding = $__default['default'](element).data('padding-right');
            $__default['default'](element).removeData('padding-right');
            element.style.paddingRight = padding ? padding : '';
          }); // Restore sticky content
    
          var elements = [].slice.call(document.querySelectorAll("" + SELECTOR_STICKY_CONTENT));
          $__default['default'](elements).each(function (index, element) {
            var margin = $__default['default'](element).data('margin-right');
    
            if (typeof margin !== 'undefined') {
              $__default['default'](element).css('margin-right', margin).removeData('margin-right');
            }
          }); // Restore body padding
    
          var padding = $__default['default'](document.body).data('padding-right');
          $__default['default'](document.body).removeData('padding-right');
          document.body.style.paddingRight = padding ? padding : '';
        };
    
        _proto._getScrollbarWidth = function _getScrollbarWidth() {
          // thx d.walsh
          var scrollDiv = document.createElement('div');
          scrollDiv.className = CLASS_NAME_SCROLLBAR_MEASURER;
          document.body.appendChild(scrollDiv);
          var scrollbarWidth = scrollDiv.getBoundingClientRect().width - scrollDiv.clientWidth;
          document.body.removeChild(scrollDiv);
          return scrollbarWidth;
        } // Static
        ;
    
        Modal._jQueryInterface = function _jQueryInterface(config, relatedTarget) {
          return this.each(function () {
            var data = $__default['default'](this).data(DATA_KEY);
    
            var _config = _extends({}, Default, $__default['default'](this).data(), typeof config === 'object' && config ? config : {});
    
            if (!data) {
              data = new Modal(this, _config);
              $__default['default'](this).data(DATA_KEY, data);
            }
    
            if (typeof config === 'string') {
              if (typeof data[config] === 'undefined') {
                throw new TypeError("No method named \"" + config + "\"");
              }
    
              data[config](relatedTarget);
            } else if (_config.show) {
              data.show(relatedTarget);
            }
          });
        };
    
        _createClass(Modal, null, [{
          key: "VERSION",
          get: function get() {
            return VERSION;
          }
        }, {
          key: "Default",
          get: function get() {
            return Default;
          }
        }]);
    
        return Modal;
      }();
      /**
       * ------------------------------------------------------------------------
       * Data Api implementation
       * ------------------------------------------------------------------------
       */
    
    
      $__default['default'](document).on(EVENT_CLICK_DATA_API, SELECTOR_DATA_TOGGLE, function (event) {
        var _this11 = this;
    
        var target;
        var selector = Util__default['default'].getSelectorFromElement(this);
    
        if (selector) {
          target = document.querySelector(selector);
        }
    
        var config = $__default['default'](target).data(DATA_KEY) ? 'toggle' : _extends({}, $__default['default'](target).data(), $__default['default'](this).data());
    
        if (this.tagName === 'A' || this.tagName === 'AREA') {
          event.preventDefault();
        }
    
        var $target = $__default['default'](target).one(EVENT_SHOW, function (showEvent) {
          if (showEvent.isDefaultPrevented()) {
            // Only register focus restorer if modal will actually get shown
            return;
          }
    
          $target.one(EVENT_HIDDEN, function () {
            if ($__default['default'](_this11).is(':visible')) {
              _this11.focus();
            }
          });
        });
    
        Modal._jQueryInterface.call($__default['default'](target), config, this);
      });
      /**
       * ------------------------------------------------------------------------
       * jQuery
       * ------------------------------------------------------------------------
       */
    
      $__default['default'].fn[NAME] = Modal._jQueryInterface;
      $__default['default'].fn[NAME].Constructor = Modal;
    
      $__default['default'].fn[NAME].noConflict = function () {
        $__default['default'].fn[NAME] = JQUERY_NO_CONFLICT;
        return Modal._jQueryInterface;
      };
    
      return Modal;
    
    })));
    //# sourceMappingURL=modal.js.map
    
    
    /***/ }),
    
    /***/ "./node_modules/bootstrap/js/dist/util.js":
    /*!************************************************!*\
      !*** ./node_modules/bootstrap/js/dist/util.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    /*!
      * Bootstrap util.js v4.6.0 (https://getbootstrap.com/)
      * Copyright 2011-2021 The Bootstrap Authors (https://github.com/twbs/bootstrap/graphs/contributors)
      * Licensed under MIT (https://github.com/twbs/bootstrap/blob/main/LICENSE)
      */
    (function (global, factory) {
       true ? module.exports = factory(__webpack_require__(/*! jquery */ "./node_modules/jquery/dist/jquery.js")) :
      undefined;
    }(this, (function ($) { 'use strict';
    
      function _interopDefaultLegacy (e) { return e && typeof e === 'object' && 'default' in e ? e : { 'default': e }; }
    
      var $__default = /*#__PURE__*/_interopDefaultLegacy($);
    
      /**
       * --------------------------------------------------------------------------
       * Bootstrap (v4.6.0): util.js
       * Licensed under MIT (https://github.com/twbs/bootstrap/blob/main/LICENSE)
       * --------------------------------------------------------------------------
       */
      /**
       * ------------------------------------------------------------------------
       * Private TransitionEnd Helpers
       * ------------------------------------------------------------------------
       */
    
      var TRANSITION_END = 'transitionend';
      var MAX_UID = 1000000;
      var MILLISECONDS_MULTIPLIER = 1000; // Shoutout AngusCroll (https://goo.gl/pxwQGp)
    
      function toType(obj) {
        if (obj === null || typeof obj === 'undefined') {
          return "" + obj;
        }
    
        return {}.toString.call(obj).match(/\s([a-z]+)/i)[1].toLowerCase();
      }
    
      function getSpecialTransitionEndEvent() {
        return {
          bindType: TRANSITION_END,
          delegateType: TRANSITION_END,
          handle: function handle(event) {
            if ($__default['default'](event.target).is(this)) {
              return event.handleObj.handler.apply(this, arguments); // eslint-disable-line prefer-rest-params
            }
    
            return undefined;
          }
        };
      }
    
      function transitionEndEmulator(duration) {
        var _this = this;
    
        var called = false;
        $__default['default'](this).one(Util.TRANSITION_END, function () {
          called = true;
        });
        setTimeout(function () {
          if (!called) {
            Util.triggerTransitionEnd(_this);
          }
        }, duration);
        return this;
      }
    
      function setTransitionEndSupport() {
        $__default['default'].fn.emulateTransitionEnd = transitionEndEmulator;
        $__default['default'].event.special[Util.TRANSITION_END] = getSpecialTransitionEndEvent();
      }
      /**
       * --------------------------------------------------------------------------
       * Public Util Api
       * --------------------------------------------------------------------------
       */
    
    
      var Util = {
        TRANSITION_END: 'bsTransitionEnd',
        getUID: function getUID(prefix) {
          do {
            prefix += ~~(Math.random() * MAX_UID); // "~~" acts like a faster Math.floor() here
          } while (document.getElementById(prefix));
    
          return prefix;
        },
        getSelectorFromElement: function getSelectorFromElement(element) {
          var selector = element.getAttribute('data-target');
    
          if (!selector || selector === '#') {
            var hrefAttr = element.getAttribute('href');
            selector = hrefAttr && hrefAttr !== '#' ? hrefAttr.trim() : '';
          }
    
          try {
            return document.querySelector(selector) ? selector : null;
          } catch (_) {
            return null;
          }
        },
        getTransitionDurationFromElement: function getTransitionDurationFromElement(element) {
          if (!element) {
            return 0;
          } // Get transition-duration of the element
    
    
          var transitionDuration = $__default['default'](element).css('transition-duration');
          var transitionDelay = $__default['default'](element).css('transition-delay');
          var floatTransitionDuration = parseFloat(transitionDuration);
          var floatTransitionDelay = parseFloat(transitionDelay); // Return 0 if element or transition duration is not found
    
          if (!floatTransitionDuration && !floatTransitionDelay) {
            return 0;
          } // If multiple durations are defined, take the first
    
    
          transitionDuration = transitionDuration.split(',')[0];
          transitionDelay = transitionDelay.split(',')[0];
          return (parseFloat(transitionDuration) + parseFloat(transitionDelay)) * MILLISECONDS_MULTIPLIER;
        },
        reflow: function reflow(element) {
          return element.offsetHeight;
        },
        triggerTransitionEnd: function triggerTransitionEnd(element) {
          $__default['default'](element).trigger(TRANSITION_END);
        },
        supportsTransitionEnd: function supportsTransitionEnd() {
          return Boolean(TRANSITION_END);
        },
        isElement: function isElement(obj) {
          return (obj[0] || obj).nodeType;
        },
        typeCheckConfig: function typeCheckConfig(componentName, config, configTypes) {
          for (var property in configTypes) {
            if (Object.prototype.hasOwnProperty.call(configTypes, property)) {
              var expectedTypes = configTypes[property];
              var value = config[property];
              var valueType = value && Util.isElement(value) ? 'element' : toType(value);
    
              if (!new RegExp(expectedTypes).test(valueType)) {
                throw new Error(componentName.toUpperCase() + ": " + ("Option \"" + property + "\" provided type \"" + valueType + "\" ") + ("but expected type \"" + expectedTypes + "\"."));
              }
            }
          }
        },
        findShadowRoot: function findShadowRoot(element) {
          if (!document.documentElement.attachShadow) {
            return null;
          } // Can find the shadow root otherwise it'll return the document
    
    
          if (typeof element.getRootNode === 'function') {
            var root = element.getRootNode();
            return root instanceof ShadowRoot ? root : null;
          }
    
          if (element instanceof ShadowRoot) {
            return element;
          } // when we don't find a shadow root
    
    
          if (!element.parentNode) {
            return null;
          }
    
          return Util.findShadowRoot(element.parentNode);
        },
        jQueryDetection: function jQueryDetection() {
          if (typeof $__default['default'] === 'undefined') {
            throw new TypeError('Bootstrap\'s JavaScript requires jQuery. jQuery must be included before Bootstrap\'s JavaScript.');
          }
    
          var version = $__default['default'].fn.jquery.split(' ')[0].split('.');
          var minMajor = 1;
          var ltMajor = 2;
          var minMinor = 9;
          var minPatch = 1;
          var maxMajor = 4;
    
          if (version[0] < ltMajor && version[1] < minMinor || version[0] === minMajor && version[1] === minMinor && version[2] < minPatch || version[0] >= maxMajor) {
            throw new Error('Bootstrap\'s JavaScript requires at least jQuery v1.9.1 but less than v4.0.0');
          }
        }
      };
      Util.jQueryDetection();
      setTransitionEndSupport();
    
      return Util;
    
    })));
    //# sourceMappingURL=util.js.map
    
    
    /***/ }),
    
    /***/ "./node_modules/global/document.js":
    /*!*****************************************!*\
      !*** ./node_modules/global/document.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    /* WEBPACK VAR INJECTION */(function(global) {var topLevel = typeof global !== 'undefined' ? global :
        typeof window !== 'undefined' ? window : {}
    var minDoc = __webpack_require__(/*! min-document */ 2);
    
    var doccy;
    
    if (typeof document !== 'undefined') {
        doccy = document;
    } else {
        doccy = topLevel['__GLOBAL_DOCUMENT_CACHE@4'];
    
        if (!doccy) {
            doccy = topLevel['__GLOBAL_DOCUMENT_CACHE@4'] = minDoc;
        }
    }
    
    module.exports = doccy;
    
    /* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./../webpack/buildin/global.js */ "./node_modules/webpack/buildin/global.js")))
    
    /***/ }),
    
    /***/ "./node_modules/global/window.js":
    /*!***************************************!*\
      !*** ./node_modules/global/window.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    /* WEBPACK VAR INJECTION */(function(global) {var win;
    
    if (typeof window !== "undefined") {
        win = window;
    } else if (typeof global !== "undefined") {
        win = global;
    } else if (typeof self !== "undefined"){
        win = self;
    } else {
        win = {};
    }
    
    module.exports = win;
    
    /* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./../webpack/buildin/global.js */ "./node_modules/webpack/buildin/global.js")))
    
    /***/ }),
    
    /***/ "./node_modules/jquery/dist/jquery.js":
    /*!********************************************!*\
      !*** ./node_modules/jquery/dist/jquery.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var __WEBPACK_AMD_DEFINE_ARRAY__, __WEBPACK_AMD_DEFINE_RESULT__;/*!
     * jQuery JavaScript Library v3.6.0
     * https://jquery.com/
     *
     * Includes Sizzle.js
     * https://sizzlejs.com/
     *
     * Copyright OpenJS Foundation and other contributors
     * Released under the MIT license
     * https://jquery.org/license
     *
     * Date: 2021-03-02T17:08Z
     */
    ( function( global, factory ) {
    
        "use strict";
    
        if (  true && typeof module.exports === "object" ) {
    
            // For CommonJS and CommonJS-like environments where a proper `window`
            // is present, execute the factory and get jQuery.
            // For environments that do not have a `window` with a `document`
            // (such as Node.js), expose a factory as module.exports.
            // This accentuates the need for the creation of a real `window`.
            // e.g. var jQuery = require("jquery")(window);
            // See ticket #14549 for more info.
            module.exports = global.document ?
                factory( global, true ) :
                function( w ) {
                    if ( !w.document ) {
                        throw new Error( "jQuery requires a window with a document" );
                    }
                    return factory( w );
                };
        } else {
            factory( global );
        }
    
    // Pass this if window is not defined yet
    } )( typeof window !== "undefined" ? window : this, function( window, noGlobal ) {
    
    // Edge <= 12 - 13+, Firefox <=18 - 45+, IE 10 - 11, Safari 5.1 - 9+, iOS 6 - 9.1
    // throw exceptions when non-strict code (e.g., ASP.NET 4.5) accesses strict mode
    // arguments.callee.caller (trac-13335). But as of jQuery 3.0 (2016), strict mode should be common
    // enough that all such attempts are guarded in a try block.
    "use strict";
    
    var arr = [];
    
    var getProto = Object.getPrototypeOf;
    
    var slice = arr.slice;
    
    var flat = arr.flat ? function( array ) {
        return arr.flat.call( array );
    } : function( array ) {
        return arr.concat.apply( [], array );
    };
    
    
    var push = arr.push;
    
    var indexOf = arr.indexOf;
    
    var class2type = {};
    
    var toString = class2type.toString;
    
    var hasOwn = class2type.hasOwnProperty;
    
    var fnToString = hasOwn.toString;
    
    var ObjectFunctionString = fnToString.call( Object );
    
    var support = {};
    
    var isFunction = function isFunction( obj ) {
    
            // Support: Chrome <=57, Firefox <=52
            // In some browsers, typeof returns "function" for HTML <object> elements
            // (i.e., `typeof document.createElement( "object" ) === "function"`).
            // We don't want to classify *any* DOM node as a function.
            // Support: QtWeb <=3.8.5, WebKit <=534.34, wkhtmltopdf tool <=0.12.5
            // Plus for old WebKit, typeof returns "function" for HTML collections
            // (e.g., `typeof document.getElementsByTagName("div") === "function"`). (gh-4756)
            return typeof obj === "function" && typeof obj.nodeType !== "number" &&
                typeof obj.item !== "function";
        };
    
    
    var isWindow = function isWindow( obj ) {
            return obj != null && obj === obj.window;
        };
    
    
    var document = window.document;
    
    
    
        var preservedScriptAttributes = {
            type: true,
            src: true,
            nonce: true,
            noModule: true
        };
    
        function DOMEval( code, node, doc ) {
            doc = doc || document;
    
            var i, val,
                script = doc.createElement( "script" );
    
            script.text = code;
            if ( node ) {
                for ( i in preservedScriptAttributes ) {
    
                    // Support: Firefox 64+, Edge 18+
                    // Some browsers don't support the "nonce" property on scripts.
                    // On the other hand, just using `getAttribute` is not enough as
                    // the `nonce` attribute is reset to an empty string whenever it
                    // becomes browsing-context connected.
                    // See https://github.com/whatwg/html/issues/2369
                    // See https://html.spec.whatwg.org/#nonce-attributes
                    // The `node.getAttribute` check was added for the sake of
                    // `jQuery.globalEval` so that it can fake a nonce-containing node
                    // via an object.
                    val = node[ i ] || node.getAttribute && node.getAttribute( i );
                    if ( val ) {
                        script.setAttribute( i, val );
                    }
                }
            }
            doc.head.appendChild( script ).parentNode.removeChild( script );
        }
    
    
    function toType( obj ) {
        if ( obj == null ) {
            return obj + "";
        }
    
        // Support: Android <=2.3 only (functionish RegExp)
        return typeof obj === "object" || typeof obj === "function" ?
            class2type[ toString.call( obj ) ] || "object" :
            typeof obj;
    }
    /* global Symbol */
    // Defining this global in .eslintrc.json would create a danger of using the global
    // unguarded in another place, it seems safer to define global only for this module
    
    
    
    var
        version = "3.6.0",
    
        // Define a local copy of jQuery
        jQuery = function( selector, context ) {
    
            // The jQuery object is actually just the init constructor 'enhanced'
            // Need init if jQuery is called (just allow error to be thrown if not included)
            return new jQuery.fn.init( selector, context );
        };
    
    jQuery.fn = jQuery.prototype = {
    
        // The current version of jQuery being used
        jquery: version,
    
        constructor: jQuery,
    
        // The default length of a jQuery object is 0
        length: 0,
    
        toArray: function() {
            return slice.call( this );
        },
    
        // Get the Nth element in the matched element set OR
        // Get the whole matched element set as a clean array
        get: function( num ) {
    
            // Return all the elements in a clean array
            if ( num == null ) {
                return slice.call( this );
            }
    
            // Return just the one element from the set
            return num < 0 ? this[ num + this.length ] : this[ num ];
        },
    
        // Take an array of elements and push it onto the stack
        // (returning the new matched element set)
        pushStack: function( elems ) {
    
            // Build a new jQuery matched element set
            var ret = jQuery.merge( this.constructor(), elems );
    
            // Add the old object onto the stack (as a reference)
            ret.prevObject = this;
    
            // Return the newly-formed element set
            return ret;
        },
    
        // Execute a callback for every element in the matched set.
        each: function( callback ) {
            return jQuery.each( this, callback );
        },
    
        map: function( callback ) {
            return this.pushStack( jQuery.map( this, function( elem, i ) {
                return callback.call( elem, i, elem );
            } ) );
        },
    
        slice: function() {
            return this.pushStack( slice.apply( this, arguments ) );
        },
    
        first: function() {
            return this.eq( 0 );
        },
    
        last: function() {
            return this.eq( -1 );
        },
    
        even: function() {
            return this.pushStack( jQuery.grep( this, function( _elem, i ) {
                return ( i + 1 ) % 2;
            } ) );
        },
    
        odd: function() {
            return this.pushStack( jQuery.grep( this, function( _elem, i ) {
                return i % 2;
            } ) );
        },
    
        eq: function( i ) {
            var len = this.length,
                j = +i + ( i < 0 ? len : 0 );
            return this.pushStack( j >= 0 && j < len ? [ this[ j ] ] : [] );
        },
    
        end: function() {
            return this.prevObject || this.constructor();
        },
    
        // For internal use only.
        // Behaves like an Array's method, not like a jQuery method.
        push: push,
        sort: arr.sort,
        splice: arr.splice
    };
    
    jQuery.extend = jQuery.fn.extend = function() {
        var options, name, src, copy, copyIsArray, clone,
            target = arguments[ 0 ] || {},
            i = 1,
            length = arguments.length,
            deep = false;
    
        // Handle a deep copy situation
        if ( typeof target === "boolean" ) {
            deep = target;
    
            // Skip the boolean and the target
            target = arguments[ i ] || {};
            i++;
        }
    
        // Handle case when target is a string or something (possible in deep copy)
        if ( typeof target !== "object" && !isFunction( target ) ) {
            target = {};
        }
    
        // Extend jQuery itself if only one argument is passed
        if ( i === length ) {
            target = this;
            i--;
        }
    
        for ( ; i < length; i++ ) {
    
            // Only deal with non-null/undefined values
            if ( ( options = arguments[ i ] ) != null ) {
    
                // Extend the base object
                for ( name in options ) {
                    copy = options[ name ];
    
                    // Prevent Object.prototype pollution
                    // Prevent never-ending loop
                    if ( name === "__proto__" || target === copy ) {
                        continue;
                    }
    
                    // Recurse if we're merging plain objects or arrays
                    if ( deep && copy && ( jQuery.isPlainObject( copy ) ||
                        ( copyIsArray = Array.isArray( copy ) ) ) ) {
                        src = target[ name ];
    
                        // Ensure proper type for the source value
                        if ( copyIsArray && !Array.isArray( src ) ) {
                            clone = [];
                        } else if ( !copyIsArray && !jQuery.isPlainObject( src ) ) {
                            clone = {};
                        } else {
                            clone = src;
                        }
                        copyIsArray = false;
    
                        // Never move original objects, clone them
                        target[ name ] = jQuery.extend( deep, clone, copy );
    
                    // Don't bring in undefined values
                    } else if ( copy !== undefined ) {
                        target[ name ] = copy;
                    }
                }
            }
        }
    
        // Return the modified object
        return target;
    };
    
    jQuery.extend( {
    
        // Unique for each copy of jQuery on the page
        expando: "jQuery" + ( version + Math.random() ).replace( /\D/g, "" ),
    
        // Assume jQuery is ready without the ready module
        isReady: true,
    
        error: function( msg ) {
            throw new Error( msg );
        },
    
        noop: function() {},
    
        isPlainObject: function( obj ) {
            var proto, Ctor;
    
            // Detect obvious negatives
            // Use toString instead of jQuery.type to catch host objects
            if ( !obj || toString.call( obj ) !== "[object Object]" ) {
                return false;
            }
    
            proto = getProto( obj );
    
            // Objects with no prototype (e.g., `Object.create( null )`) are plain
            if ( !proto ) {
                return true;
            }
    
            // Objects with prototype are plain iff they were constructed by a global Object function
            Ctor = hasOwn.call( proto, "constructor" ) && proto.constructor;
            return typeof Ctor === "function" && fnToString.call( Ctor ) === ObjectFunctionString;
        },
    
        isEmptyObject: function( obj ) {
            var name;
    
            for ( name in obj ) {
                return false;
            }
            return true;
        },
    
        // Evaluates a script in a provided context; falls back to the global one
        // if not specified.
        globalEval: function( code, options, doc ) {
            DOMEval( code, { nonce: options && options.nonce }, doc );
        },
    
        each: function( obj, callback ) {
            var length, i = 0;
    
            if ( isArrayLike( obj ) ) {
                length = obj.length;
                for ( ; i < length; i++ ) {
                    if ( callback.call( obj[ i ], i, obj[ i ] ) === false ) {
                        break;
                    }
                }
            } else {
                for ( i in obj ) {
                    if ( callback.call( obj[ i ], i, obj[ i ] ) === false ) {
                        break;
                    }
                }
            }
    
            return obj;
        },
    
        // results is for internal usage only
        makeArray: function( arr, results ) {
            var ret = results || [];
    
            if ( arr != null ) {
                if ( isArrayLike( Object( arr ) ) ) {
                    jQuery.merge( ret,
                        typeof arr === "string" ?
                            [ arr ] : arr
                    );
                } else {
                    push.call( ret, arr );
                }
            }
    
            return ret;
        },
    
        inArray: function( elem, arr, i ) {
            return arr == null ? -1 : indexOf.call( arr, elem, i );
        },
    
        // Support: Android <=4.0 only, PhantomJS 1 only
        // push.apply(_, arraylike) throws on ancient WebKit
        merge: function( first, second ) {
            var len = +second.length,
                j = 0,
                i = first.length;
    
            for ( ; j < len; j++ ) {
                first[ i++ ] = second[ j ];
            }
    
            first.length = i;
    
            return first;
        },
    
        grep: function( elems, callback, invert ) {
            var callbackInverse,
                matches = [],
                i = 0,
                length = elems.length,
                callbackExpect = !invert;
    
            // Go through the array, only saving the items
            // that pass the validator function
            for ( ; i < length; i++ ) {
                callbackInverse = !callback( elems[ i ], i );
                if ( callbackInverse !== callbackExpect ) {
                    matches.push( elems[ i ] );
                }
            }
    
            return matches;
        },
    
        // arg is for internal usage only
        map: function( elems, callback, arg ) {
            var length, value,
                i = 0,
                ret = [];
    
            // Go through the array, translating each of the items to their new values
            if ( isArrayLike( elems ) ) {
                length = elems.length;
                for ( ; i < length; i++ ) {
                    value = callback( elems[ i ], i, arg );
    
                    if ( value != null ) {
                        ret.push( value );
                    }
                }
    
            // Go through every key on the object,
            } else {
                for ( i in elems ) {
                    value = callback( elems[ i ], i, arg );
    
                    if ( value != null ) {
                        ret.push( value );
                    }
                }
            }
    
            // Flatten any nested arrays
            return flat( ret );
        },
    
        // A global GUID counter for objects
        guid: 1,
    
        // jQuery.support is not used in Core but other projects attach their
        // properties to it so it needs to exist.
        support: support
    } );
    
    if ( typeof Symbol === "function" ) {
        jQuery.fn[ Symbol.iterator ] = arr[ Symbol.iterator ];
    }
    
    // Populate the class2type map
    jQuery.each( "Boolean Number String Function Array Date RegExp Object Error Symbol".split( " " ),
        function( _i, name ) {
            class2type[ "[object " + name + "]" ] = name.toLowerCase();
        } );
    
    function isArrayLike( obj ) {
    
        // Support: real iOS 8.2 only (not reproducible in simulator)
        // `in` check used to prevent JIT error (gh-2145)
        // hasOwn isn't used here due to false negatives
        // regarding Nodelist length in IE
        var length = !!obj && "length" in obj && obj.length,
            type = toType( obj );
    
        if ( isFunction( obj ) || isWindow( obj ) ) {
            return false;
        }
    
        return type === "array" || length === 0 ||
            typeof length === "number" && length > 0 && ( length - 1 ) in obj;
    }
    var Sizzle =
    /*!
     * Sizzle CSS Selector Engine v2.3.6
     * https://sizzlejs.com/
     *
     * Copyright JS Foundation and other contributors
     * Released under the MIT license
     * https://js.foundation/
     *
     * Date: 2021-02-16
     */
    ( function( window ) {
    var i,
        support,
        Expr,
        getText,
        isXML,
        tokenize,
        compile,
        select,
        outermostContext,
        sortInput,
        hasDuplicate,
    
        // Local document vars
        setDocument,
        document,
        docElem,
        documentIsHTML,
        rbuggyQSA,
        rbuggyMatches,
        matches,
        contains,
    
        // Instance-specific data
        expando = "sizzle" + 1 * new Date(),
        preferredDoc = window.document,
        dirruns = 0,
        done = 0,
        classCache = createCache(),
        tokenCache = createCache(),
        compilerCache = createCache(),
        nonnativeSelectorCache = createCache(),
        sortOrder = function( a, b ) {
            if ( a === b ) {
                hasDuplicate = true;
            }
            return 0;
        },
    
        // Instance methods
        hasOwn = ( {} ).hasOwnProperty,
        arr = [],
        pop = arr.pop,
        pushNative = arr.push,
        push = arr.push,
        slice = arr.slice,
    
        // Use a stripped-down indexOf as it's faster than native
        // https://jsperf.com/thor-indexof-vs-for/5
        indexOf = function( list, elem ) {
            var i = 0,
                len = list.length;
            for ( ; i < len; i++ ) {
                if ( list[ i ] === elem ) {
                    return i;
                }
            }
            return -1;
        },
    
        booleans = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|" +
            "ismap|loop|multiple|open|readonly|required|scoped",
    
        // Regular expressions
    
        // http://www.w3.org/TR/css3-selectors/#whitespace
        whitespace = "[\\x20\\t\\r\\n\\f]",
    
        // https://www.w3.org/TR/css-syntax-3/#ident-token-diagram
        identifier = "(?:\\\\[\\da-fA-F]{1,6}" + whitespace +
            "?|\\\\[^\\r\\n\\f]|[\\w-]|[^\0-\\x7f])+",
    
        // Attribute selectors: http://www.w3.org/TR/selectors/#attribute-selectors
        attributes = "\\[" + whitespace + "*(" + identifier + ")(?:" + whitespace +
    
            // Operator (capture 2)
            "*([*^$|!~]?=)" + whitespace +
    
            // "Attribute values must be CSS identifiers [capture 5]
            // or strings [capture 3 or capture 4]"
            "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + identifier + "))|)" +
            whitespace + "*\\]",
    
        pseudos = ":(" + identifier + ")(?:\\((" +
    
            // To reduce the number of selectors needing tokenize in the preFilter, prefer arguments:
            // 1. quoted (capture 3; capture 4 or capture 5)
            "('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|" +
    
            // 2. simple (capture 6)
            "((?:\\\\.|[^\\\\()[\\]]|" + attributes + ")*)|" +
    
            // 3. anything else (capture 2)
            ".*" +
            ")\\)|)",
    
        // Leading and non-escaped trailing whitespace, capturing some non-whitespace characters preceding the latter
        rwhitespace = new RegExp( whitespace + "+", "g" ),
        rtrim = new RegExp( "^" + whitespace + "+|((?:^|[^\\\\])(?:\\\\.)*)" +
            whitespace + "+$", "g" ),
    
        rcomma = new RegExp( "^" + whitespace + "*," + whitespace + "*" ),
        rcombinators = new RegExp( "^" + whitespace + "*([>+~]|" + whitespace + ")" + whitespace +
            "*" ),
        rdescend = new RegExp( whitespace + "|>" ),
    
        rpseudo = new RegExp( pseudos ),
        ridentifier = new RegExp( "^" + identifier + "$" ),
    
        matchExpr = {
            "ID": new RegExp( "^#(" + identifier + ")" ),
            "CLASS": new RegExp( "^\\.(" + identifier + ")" ),
            "TAG": new RegExp( "^(" + identifier + "|[*])" ),
            "ATTR": new RegExp( "^" + attributes ),
            "PSEUDO": new RegExp( "^" + pseudos ),
            "CHILD": new RegExp( "^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" +
                whitespace + "*(even|odd|(([+-]|)(\\d*)n|)" + whitespace + "*(?:([+-]|)" +
                whitespace + "*(\\d+)|))" + whitespace + "*\\)|)", "i" ),
            "bool": new RegExp( "^(?:" + booleans + ")$", "i" ),
    
            // For use in libraries implementing .is()
            // We use this for POS matching in `select`
            "needsContext": new RegExp( "^" + whitespace +
                "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + whitespace +
                "*((?:-\\d)?\\d*)" + whitespace + "*\\)|)(?=[^-]|$)", "i" )
        },
    
        rhtml = /HTML$/i,
        rinputs = /^(?:input|select|textarea|button)$/i,
        rheader = /^h\d$/i,
    
        rnative = /^[^{]+\{\s*\[native \w/,
    
        // Easily-parseable/retrievable ID or TAG or CLASS selectors
        rquickExpr = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/,
    
        rsibling = /[+~]/,
    
        // CSS escapes
        // http://www.w3.org/TR/CSS21/syndata.html#escaped-characters
        runescape = new RegExp( "\\\\[\\da-fA-F]{1,6}" + whitespace + "?|\\\\([^\\r\\n\\f])", "g" ),
        funescape = function( escape, nonHex ) {
            var high = "0x" + escape.slice( 1 ) - 0x10000;
    
            return nonHex ?
    
                // Strip the backslash prefix from a non-hex escape sequence
                nonHex :
    
                // Replace a hexadecimal escape sequence with the encoded Unicode code point
                // Support: IE <=11+
                // For values outside the Basic Multilingual Plane (BMP), manually construct a
                // surrogate pair
                high < 0 ?
                    String.fromCharCode( high + 0x10000 ) :
                    String.fromCharCode( high >> 10 | 0xD800, high & 0x3FF | 0xDC00 );
        },
    
        // CSS string/identifier serialization
        // https://drafts.csswg.org/cssom/#common-serializing-idioms
        rcssescape = /([\0-\x1f\x7f]|^-?\d)|^-$|[^\0-\x1f\x7f-\uFFFF\w-]/g,
        fcssescape = function( ch, asCodePoint ) {
            if ( asCodePoint ) {
    
                // U+0000 NULL becomes U+FFFD REPLACEMENT CHARACTER
                if ( ch === "\0" ) {
                    return "\uFFFD";
                }
    
                // Control characters and (dependent upon position) numbers get escaped as code points
                return ch.slice( 0, -1 ) + "\\" +
                    ch.charCodeAt( ch.length - 1 ).toString( 16 ) + " ";
            }
    
            // Other potentially-special ASCII characters get backslash-escaped
            return "\\" + ch;
        },
    
        // Used for iframes
        // See setDocument()
        // Removing the function wrapper causes a "Permission Denied"
        // error in IE
        unloadHandler = function() {
            setDocument();
        },
    
        inDisabledFieldset = addCombinator(
            function( elem ) {
                return elem.disabled === true && elem.nodeName.toLowerCase() === "fieldset";
            },
            { dir: "parentNode", next: "legend" }
        );
    
    // Optimize for push.apply( _, NodeList )
    try {
        push.apply(
            ( arr = slice.call( preferredDoc.childNodes ) ),
            preferredDoc.childNodes
        );
    
        // Support: Android<4.0
        // Detect silently failing push.apply
        // eslint-disable-next-line no-unused-expressions
        arr[ preferredDoc.childNodes.length ].nodeType;
    } catch ( e ) {
        push = { apply: arr.length ?
    
            // Leverage slice if possible
            function( target, els ) {
                pushNative.apply( target, slice.call( els ) );
            } :
    
            // Support: IE<9
            // Otherwise append directly
            function( target, els ) {
                var j = target.length,
                    i = 0;
    
                // Can't trust NodeList.length
                while ( ( target[ j++ ] = els[ i++ ] ) ) {}
                target.length = j - 1;
            }
        };
    }
    
    function Sizzle( selector, context, results, seed ) {
        var m, i, elem, nid, match, groups, newSelector,
            newContext = context && context.ownerDocument,
    
            // nodeType defaults to 9, since context defaults to document
            nodeType = context ? context.nodeType : 9;
    
        results = results || [];
    
        // Return early from calls with invalid selector or context
        if ( typeof selector !== "string" || !selector ||
            nodeType !== 1 && nodeType !== 9 && nodeType !== 11 ) {
    
            return results;
        }
    
        // Try to shortcut find operations (as opposed to filters) in HTML documents
        if ( !seed ) {
            setDocument( context );
            context = context || document;
    
            if ( documentIsHTML ) {
    
                // If the selector is sufficiently simple, try using a "get*By*" DOM method
                // (excepting DocumentFragment context, where the methods don't exist)
                if ( nodeType !== 11 && ( match = rquickExpr.exec( selector ) ) ) {
    
                    // ID selector
                    if ( ( m = match[ 1 ] ) ) {
    
                        // Document context
                        if ( nodeType === 9 ) {
                            if ( ( elem = context.getElementById( m ) ) ) {
    
                                // Support: IE, Opera, Webkit
                                // TODO: identify versions
                                // getElementById can match elements by name instead of ID
                                if ( elem.id === m ) {
                                    results.push( elem );
                                    return results;
                                }
                            } else {
                                return results;
                            }
    
                        // Element context
                        } else {
    
                            // Support: IE, Opera, Webkit
                            // TODO: identify versions
                            // getElementById can match elements by name instead of ID
                            if ( newContext && ( elem = newContext.getElementById( m ) ) &&
                                contains( context, elem ) &&
                                elem.id === m ) {
    
                                results.push( elem );
                                return results;
                            }
                        }
    
                    // Type selector
                    } else if ( match[ 2 ] ) {
                        push.apply( results, context.getElementsByTagName( selector ) );
                        return results;
    
                    // Class selector
                    } else if ( ( m = match[ 3 ] ) && support.getElementsByClassName &&
                        context.getElementsByClassName ) {
    
                        push.apply( results, context.getElementsByClassName( m ) );
                        return results;
                    }
                }
    
                // Take advantage of querySelectorAll
                if ( support.qsa &&
                    !nonnativeSelectorCache[ selector + " " ] &&
                    ( !rbuggyQSA || !rbuggyQSA.test( selector ) ) &&
    
                    // Support: IE 8 only
                    // Exclude object elements
                    ( nodeType !== 1 || context.nodeName.toLowerCase() !== "object" ) ) {
    
                    newSelector = selector;
                    newContext = context;
    
                    // qSA considers elements outside a scoping root when evaluating child or
                    // descendant combinators, which is not what we want.
                    // In such cases, we work around the behavior by prefixing every selector in the
                    // list with an ID selector referencing the scope context.
                    // The technique has to be used as well when a leading combinator is used
                    // as such selectors are not recognized by querySelectorAll.
                    // Thanks to Andrew Dupont for this technique.
                    if ( nodeType === 1 &&
                        ( rdescend.test( selector ) || rcombinators.test( selector ) ) ) {
    
                        // Expand context for sibling selectors
                        newContext = rsibling.test( selector ) && testContext( context.parentNode ) ||
                            context;
    
                        // We can use :scope instead of the ID hack if the browser
                        // supports it & if we're not changing the context.
                        if ( newContext !== context || !support.scope ) {
    
                            // Capture the context ID, setting it first if necessary
                            if ( ( nid = context.getAttribute( "id" ) ) ) {
                                nid = nid.replace( rcssescape, fcssescape );
                            } else {
                                context.setAttribute( "id", ( nid = expando ) );
                            }
                        }
    
                        // Prefix every selector in the list
                        groups = tokenize( selector );
                        i = groups.length;
                        while ( i-- ) {
                            groups[ i ] = ( nid ? "#" + nid : ":scope" ) + " " +
                                toSelector( groups[ i ] );
                        }
                        newSelector = groups.join( "," );
                    }
    
                    try {
                        push.apply( results,
                            newContext.querySelectorAll( newSelector )
                        );
                        return results;
                    } catch ( qsaError ) {
                        nonnativeSelectorCache( selector, true );
                    } finally {
                        if ( nid === expando ) {
                            context.removeAttribute( "id" );
                        }
                    }
                }
            }
        }
    
        // All others
        return select( selector.replace( rtrim, "$1" ), context, results, seed );
    }
    
    /**
     * Create key-value caches of limited size
     * @returns {function(string, object)} Returns the Object data after storing it on itself with
     *	property name the (space-suffixed) string and (if the cache is larger than Expr.cacheLength)
     *	deleting the oldest entry
     */
    function createCache() {
        var keys = [];
    
        function cache( key, value ) {
    
            // Use (key + " ") to avoid collision with native prototype properties (see Issue #157)
            if ( keys.push( key + " " ) > Expr.cacheLength ) {
    
                // Only keep the most recent entries
                delete cache[ keys.shift() ];
            }
            return ( cache[ key + " " ] = value );
        }
        return cache;
    }
    
    /**
     * Mark a function for special use by Sizzle
     * @param {Function} fn The function to mark
     */
    function markFunction( fn ) {
        fn[ expando ] = true;
        return fn;
    }
    
    /**
     * Support testing using an element
     * @param {Function} fn Passed the created element and returns a boolean result
     */
    function assert( fn ) {
        var el = document.createElement( "fieldset" );
    
        try {
            return !!fn( el );
        } catch ( e ) {
            return false;
        } finally {
    
            // Remove from its parent by default
            if ( el.parentNode ) {
                el.parentNode.removeChild( el );
            }
    
            // release memory in IE
            el = null;
        }
    }
    
    /**
     * Adds the same handler for all of the specified attrs
     * @param {String} attrs Pipe-separated list of attributes
     * @param {Function} handler The method that will be applied
     */
    function addHandle( attrs, handler ) {
        var arr = attrs.split( "|" ),
            i = arr.length;
    
        while ( i-- ) {
            Expr.attrHandle[ arr[ i ] ] = handler;
        }
    }
    
    /**
     * Checks document order of two siblings
     * @param {Element} a
     * @param {Element} b
     * @returns {Number} Returns less than 0 if a precedes b, greater than 0 if a follows b
     */
    function siblingCheck( a, b ) {
        var cur = b && a,
            diff = cur && a.nodeType === 1 && b.nodeType === 1 &&
                a.sourceIndex - b.sourceIndex;
    
        // Use IE sourceIndex if available on both nodes
        if ( diff ) {
            return diff;
        }
    
        // Check if b follows a
        if ( cur ) {
            while ( ( cur = cur.nextSibling ) ) {
                if ( cur === b ) {
                    return -1;
                }
            }
        }
    
        return a ? 1 : -1;
    }
    
    /**
     * Returns a function to use in pseudos for input types
     * @param {String} type
     */
    function createInputPseudo( type ) {
        return function( elem ) {
            var name = elem.nodeName.toLowerCase();
            return name === "input" && elem.type === type;
        };
    }
    
    /**
     * Returns a function to use in pseudos for buttons
     * @param {String} type
     */
    function createButtonPseudo( type ) {
        return function( elem ) {
            var name = elem.nodeName.toLowerCase();
            return ( name === "input" || name === "button" ) && elem.type === type;
        };
    }
    
    /**
     * Returns a function to use in pseudos for :enabled/:disabled
     * @param {Boolean} disabled true for :disabled; false for :enabled
     */
    function createDisabledPseudo( disabled ) {
    
        // Known :disabled false positives: fieldset[disabled] > legend:nth-of-type(n+2) :can-disable
        return function( elem ) {
    
            // Only certain elements can match :enabled or :disabled
            // https://html.spec.whatwg.org/multipage/scripting.html#selector-enabled
            // https://html.spec.whatwg.org/multipage/scripting.html#selector-disabled
            if ( "form" in elem ) {
    
                // Check for inherited disabledness on relevant non-disabled elements:
                // * listed form-associated elements in a disabled fieldset
                //   https://html.spec.whatwg.org/multipage/forms.html#category-listed
                //   https://html.spec.whatwg.org/multipage/forms.html#concept-fe-disabled
                // * option elements in a disabled optgroup
                //   https://html.spec.whatwg.org/multipage/forms.html#concept-option-disabled
                // All such elements have a "form" property.
                if ( elem.parentNode && elem.disabled === false ) {
    
                    // Option elements defer to a parent optgroup if present
                    if ( "label" in elem ) {
                        if ( "label" in elem.parentNode ) {
                            return elem.parentNode.disabled === disabled;
                        } else {
                            return elem.disabled === disabled;
                        }
                    }
    
                    // Support: IE 6 - 11
                    // Use the isDisabled shortcut property to check for disabled fieldset ancestors
                    return elem.isDisabled === disabled ||
    
                        // Where there is no isDisabled, check manually
                        /* jshint -W018 */
                        elem.isDisabled !== !disabled &&
                        inDisabledFieldset( elem ) === disabled;
                }
    
                return elem.disabled === disabled;
    
            // Try to winnow out elements that can't be disabled before trusting the disabled property.
            // Some victims get caught in our net (label, legend, menu, track), but it shouldn't
            // even exist on them, let alone have a boolean value.
            } else if ( "label" in elem ) {
                return elem.disabled === disabled;
            }
    
            // Remaining elements are neither :enabled nor :disabled
            return false;
        };
    }
    
    /**
     * Returns a function to use in pseudos for positionals
     * @param {Function} fn
     */
    function createPositionalPseudo( fn ) {
        return markFunction( function( argument ) {
            argument = +argument;
            return markFunction( function( seed, matches ) {
                var j,
                    matchIndexes = fn( [], seed.length, argument ),
                    i = matchIndexes.length;
    
                // Match elements found at the specified indexes
                while ( i-- ) {
                    if ( seed[ ( j = matchIndexes[ i ] ) ] ) {
                        seed[ j ] = !( matches[ j ] = seed[ j ] );
                    }
                }
            } );
        } );
    }
    
    /**
     * Checks a node for validity as a Sizzle context
     * @param {Element|Object=} context
     * @returns {Element|Object|Boolean} The input node if acceptable, otherwise a falsy value
     */
    function testContext( context ) {
        return context && typeof context.getElementsByTagName !== "undefined" && context;
    }
    
    // Expose support vars for convenience
    support = Sizzle.support = {};
    
    /**
     * Detects XML nodes
     * @param {Element|Object} elem An element or a document
     * @returns {Boolean} True iff elem is a non-HTML XML node
     */
    isXML = Sizzle.isXML = function( elem ) {
        var namespace = elem && elem.namespaceURI,
            docElem = elem && ( elem.ownerDocument || elem ).documentElement;
    
        // Support: IE <=8
        // Assume HTML when documentElement doesn't yet exist, such as inside loading iframes
        // https://bugs.jquery.com/ticket/4833
        return !rhtml.test( namespace || docElem && docElem.nodeName || "HTML" );
    };
    
    /**
     * Sets document-related variables once based on the current document
     * @param {Element|Object} [doc] An element or document object to use to set the document
     * @returns {Object} Returns the current document
     */
    setDocument = Sizzle.setDocument = function( node ) {
        var hasCompare, subWindow,
            doc = node ? node.ownerDocument || node : preferredDoc;
    
        // Return early if doc is invalid or already selected
        // Support: IE 11+, Edge 17 - 18+
        // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
        // two documents; shallow comparisons work.
        // eslint-disable-next-line eqeqeq
        if ( doc == document || doc.nodeType !== 9 || !doc.documentElement ) {
            return document;
        }
    
        // Update global variables
        document = doc;
        docElem = document.documentElement;
        documentIsHTML = !isXML( document );
    
        // Support: IE 9 - 11+, Edge 12 - 18+
        // Accessing iframe documents after unload throws "permission denied" errors (jQuery #13936)
        // Support: IE 11+, Edge 17 - 18+
        // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
        // two documents; shallow comparisons work.
        // eslint-disable-next-line eqeqeq
        if ( preferredDoc != document &&
            ( subWindow = document.defaultView ) && subWindow.top !== subWindow ) {
    
            // Support: IE 11, Edge
            if ( subWindow.addEventListener ) {
                subWindow.addEventListener( "unload", unloadHandler, false );
    
            // Support: IE 9 - 10 only
            } else if ( subWindow.attachEvent ) {
                subWindow.attachEvent( "onunload", unloadHandler );
            }
        }
    
        // Support: IE 8 - 11+, Edge 12 - 18+, Chrome <=16 - 25 only, Firefox <=3.6 - 31 only,
        // Safari 4 - 5 only, Opera <=11.6 - 12.x only
        // IE/Edge & older browsers don't support the :scope pseudo-class.
        // Support: Safari 6.0 only
        // Safari 6.0 supports :scope but it's an alias of :root there.
        support.scope = assert( function( el ) {
            docElem.appendChild( el ).appendChild( document.createElement( "div" ) );
            return typeof el.querySelectorAll !== "undefined" &&
                !el.querySelectorAll( ":scope fieldset div" ).length;
        } );
    
        /* Attributes
        ---------------------------------------------------------------------- */
    
        // Support: IE<8
        // Verify that getAttribute really returns attributes and not properties
        // (excepting IE8 booleans)
        support.attributes = assert( function( el ) {
            el.className = "i";
            return !el.getAttribute( "className" );
        } );
    
        /* getElement(s)By*
        ---------------------------------------------------------------------- */
    
        // Check if getElementsByTagName("*") returns only elements
        support.getElementsByTagName = assert( function( el ) {
            el.appendChild( document.createComment( "" ) );
            return !el.getElementsByTagName( "*" ).length;
        } );
    
        // Support: IE<9
        support.getElementsByClassName = rnative.test( document.getElementsByClassName );
    
        // Support: IE<10
        // Check if getElementById returns elements by name
        // The broken getElementById methods don't pick up programmatically-set names,
        // so use a roundabout getElementsByName test
        support.getById = assert( function( el ) {
            docElem.appendChild( el ).id = expando;
            return !document.getElementsByName || !document.getElementsByName( expando ).length;
        } );
    
        // ID filter and find
        if ( support.getById ) {
            Expr.filter[ "ID" ] = function( id ) {
                var attrId = id.replace( runescape, funescape );
                return function( elem ) {
                    return elem.getAttribute( "id" ) === attrId;
                };
            };
            Expr.find[ "ID" ] = function( id, context ) {
                if ( typeof context.getElementById !== "undefined" && documentIsHTML ) {
                    var elem = context.getElementById( id );
                    return elem ? [ elem ] : [];
                }
            };
        } else {
            Expr.filter[ "ID" ] =  function( id ) {
                var attrId = id.replace( runescape, funescape );
                return function( elem ) {
                    var node = typeof elem.getAttributeNode !== "undefined" &&
                        elem.getAttributeNode( "id" );
                    return node && node.value === attrId;
                };
            };
    
            // Support: IE 6 - 7 only
            // getElementById is not reliable as a find shortcut
            Expr.find[ "ID" ] = function( id, context ) {
                if ( typeof context.getElementById !== "undefined" && documentIsHTML ) {
                    var node, i, elems,
                        elem = context.getElementById( id );
    
                    if ( elem ) {
    
                        // Verify the id attribute
                        node = elem.getAttributeNode( "id" );
                        if ( node && node.value === id ) {
                            return [ elem ];
                        }
    
                        // Fall back on getElementsByName
                        elems = context.getElementsByName( id );
                        i = 0;
                        while ( ( elem = elems[ i++ ] ) ) {
                            node = elem.getAttributeNode( "id" );
                            if ( node && node.value === id ) {
                                return [ elem ];
                            }
                        }
                    }
    
                    return [];
                }
            };
        }
    
        // Tag
        Expr.find[ "TAG" ] = support.getElementsByTagName ?
            function( tag, context ) {
                if ( typeof context.getElementsByTagName !== "undefined" ) {
                    return context.getElementsByTagName( tag );
    
                // DocumentFragment nodes don't have gEBTN
                } else if ( support.qsa ) {
                    return context.querySelectorAll( tag );
                }
            } :
    
            function( tag, context ) {
                var elem,
                    tmp = [],
                    i = 0,
    
                    // By happy coincidence, a (broken) gEBTN appears on DocumentFragment nodes too
                    results = context.getElementsByTagName( tag );
    
                // Filter out possible comments
                if ( tag === "*" ) {
                    while ( ( elem = results[ i++ ] ) ) {
                        if ( elem.nodeType === 1 ) {
                            tmp.push( elem );
                        }
                    }
    
                    return tmp;
                }
                return results;
            };
    
        // Class
        Expr.find[ "CLASS" ] = support.getElementsByClassName && function( className, context ) {
            if ( typeof context.getElementsByClassName !== "undefined" && documentIsHTML ) {
                return context.getElementsByClassName( className );
            }
        };
    
        /* QSA/matchesSelector
        ---------------------------------------------------------------------- */
    
        // QSA and matchesSelector support
    
        // matchesSelector(:active) reports false when true (IE9/Opera 11.5)
        rbuggyMatches = [];
    
        // qSa(:focus) reports false when true (Chrome 21)
        // We allow this because of a bug in IE8/9 that throws an error
        // whenever `document.activeElement` is accessed on an iframe
        // So, we allow :focus to pass through QSA all the time to avoid the IE error
        // See https://bugs.jquery.com/ticket/13378
        rbuggyQSA = [];
    
        if ( ( support.qsa = rnative.test( document.querySelectorAll ) ) ) {
    
            // Build QSA regex
            // Regex strategy adopted from Diego Perini
            assert( function( el ) {
    
                var input;
    
                // Select is set to empty string on purpose
                // This is to test IE's treatment of not explicitly
                // setting a boolean content attribute,
                // since its presence should be enough
                // https://bugs.jquery.com/ticket/12359
                docElem.appendChild( el ).innerHTML = "<a id='" + expando + "'></a>" +
                    "<select id='" + expando + "-\r\\' msallowcapture=''>" +
                    "<option selected=''></option></select>";
    
                // Support: IE8, Opera 11-12.16
                // Nothing should be selected when empty strings follow ^= or $= or *=
                // The test attribute must be unknown in Opera but "safe" for WinRT
                // https://msdn.microsoft.com/en-us/library/ie/hh465388.aspx#attribute_section
                if ( el.querySelectorAll( "[msallowcapture^='']" ).length ) {
                    rbuggyQSA.push( "[*^$]=" + whitespace + "*(?:''|\"\")" );
                }
    
                // Support: IE8
                // Boolean attributes and "value" are not treated correctly
                if ( !el.querySelectorAll( "[selected]" ).length ) {
                    rbuggyQSA.push( "\\[" + whitespace + "*(?:value|" + booleans + ")" );
                }
    
                // Support: Chrome<29, Android<4.4, Safari<7.0+, iOS<7.0+, PhantomJS<1.9.8+
                if ( !el.querySelectorAll( "[id~=" + expando + "-]" ).length ) {
                    rbuggyQSA.push( "~=" );
                }
    
                // Support: IE 11+, Edge 15 - 18+
                // IE 11/Edge don't find elements on a `[name='']` query in some cases.
                // Adding a temporary attribute to the document before the selection works
                // around the issue.
                // Interestingly, IE 10 & older don't seem to have the issue.
                input = document.createElement( "input" );
                input.setAttribute( "name", "" );
                el.appendChild( input );
                if ( !el.querySelectorAll( "[name='']" ).length ) {
                    rbuggyQSA.push( "\\[" + whitespace + "*name" + whitespace + "*=" +
                        whitespace + "*(?:''|\"\")" );
                }
    
                // Webkit/Opera - :checked should return selected option elements
                // http://www.w3.org/TR/2011/REC-css3-selectors-20110929/#checked
                // IE8 throws error here and will not see later tests
                if ( !el.querySelectorAll( ":checked" ).length ) {
                    rbuggyQSA.push( ":checked" );
                }
    
                // Support: Safari 8+, iOS 8+
                // https://bugs.webkit.org/show_bug.cgi?id=136851
                // In-page `selector#id sibling-combinator selector` fails
                if ( !el.querySelectorAll( "a#" + expando + "+*" ).length ) {
                    rbuggyQSA.push( ".#.+[+~]" );
                }
    
                // Support: Firefox <=3.6 - 5 only
                // Old Firefox doesn't throw on a badly-escaped identifier.
                el.querySelectorAll( "\\\f" );
                rbuggyQSA.push( "[\\r\\n\\f]" );
            } );
    
            assert( function( el ) {
                el.innerHTML = "<a href='' disabled='disabled'></a>" +
                    "<select disabled='disabled'><option/></select>";
    
                // Support: Windows 8 Native Apps
                // The type and name attributes are restricted during .innerHTML assignment
                var input = document.createElement( "input" );
                input.setAttribute( "type", "hidden" );
                el.appendChild( input ).setAttribute( "name", "D" );
    
                // Support: IE8
                // Enforce case-sensitivity of name attribute
                if ( el.querySelectorAll( "[name=d]" ).length ) {
                    rbuggyQSA.push( "name" + whitespace + "*[*^$|!~]?=" );
                }
    
                // FF 3.5 - :enabled/:disabled and hidden elements (hidden elements are still enabled)
                // IE8 throws error here and will not see later tests
                if ( el.querySelectorAll( ":enabled" ).length !== 2 ) {
                    rbuggyQSA.push( ":enabled", ":disabled" );
                }
    
                // Support: IE9-11+
                // IE's :disabled selector does not pick up the children of disabled fieldsets
                docElem.appendChild( el ).disabled = true;
                if ( el.querySelectorAll( ":disabled" ).length !== 2 ) {
                    rbuggyQSA.push( ":enabled", ":disabled" );
                }
    
                // Support: Opera 10 - 11 only
                // Opera 10-11 does not throw on post-comma invalid pseudos
                el.querySelectorAll( "*,:x" );
                rbuggyQSA.push( ",.*:" );
            } );
        }
    
        if ( ( support.matchesSelector = rnative.test( ( matches = docElem.matches ||
            docElem.webkitMatchesSelector ||
            docElem.mozMatchesSelector ||
            docElem.oMatchesSelector ||
            docElem.msMatchesSelector ) ) ) ) {
    
            assert( function( el ) {
    
                // Check to see if it's possible to do matchesSelector
                // on a disconnected node (IE 9)
                support.disconnectedMatch = matches.call( el, "*" );
    
                // This should fail with an exception
                // Gecko does not error, returns false instead
                matches.call( el, "[s!='']:x" );
                rbuggyMatches.push( "!=", pseudos );
            } );
        }
    
        rbuggyQSA = rbuggyQSA.length && new RegExp( rbuggyQSA.join( "|" ) );
        rbuggyMatches = rbuggyMatches.length && new RegExp( rbuggyMatches.join( "|" ) );
    
        /* Contains
        ---------------------------------------------------------------------- */
        hasCompare = rnative.test( docElem.compareDocumentPosition );
    
        // Element contains another
        // Purposefully self-exclusive
        // As in, an element does not contain itself
        contains = hasCompare || rnative.test( docElem.contains ) ?
            function( a, b ) {
                var adown = a.nodeType === 9 ? a.documentElement : a,
                    bup = b && b.parentNode;
                return a === bup || !!( bup && bup.nodeType === 1 && (
                    adown.contains ?
                        adown.contains( bup ) :
                        a.compareDocumentPosition && a.compareDocumentPosition( bup ) & 16
                ) );
            } :
            function( a, b ) {
                if ( b ) {
                    while ( ( b = b.parentNode ) ) {
                        if ( b === a ) {
                            return true;
                        }
                    }
                }
                return false;
            };
    
        /* Sorting
        ---------------------------------------------------------------------- */
    
        // Document order sorting
        sortOrder = hasCompare ?
        function( a, b ) {
    
            // Flag for duplicate removal
            if ( a === b ) {
                hasDuplicate = true;
                return 0;
            }
    
            // Sort on method existence if only one input has compareDocumentPosition
            var compare = !a.compareDocumentPosition - !b.compareDocumentPosition;
            if ( compare ) {
                return compare;
            }
    
            // Calculate position if both inputs belong to the same document
            // Support: IE 11+, Edge 17 - 18+
            // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
            // two documents; shallow comparisons work.
            // eslint-disable-next-line eqeqeq
            compare = ( a.ownerDocument || a ) == ( b.ownerDocument || b ) ?
                a.compareDocumentPosition( b ) :
    
                // Otherwise we know they are disconnected
                1;
    
            // Disconnected nodes
            if ( compare & 1 ||
                ( !support.sortDetached && b.compareDocumentPosition( a ) === compare ) ) {
    
                // Choose the first element that is related to our preferred document
                // Support: IE 11+, Edge 17 - 18+
                // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
                // two documents; shallow comparisons work.
                // eslint-disable-next-line eqeqeq
                if ( a == document || a.ownerDocument == preferredDoc &&
                    contains( preferredDoc, a ) ) {
                    return -1;
                }
    
                // Support: IE 11+, Edge 17 - 18+
                // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
                // two documents; shallow comparisons work.
                // eslint-disable-next-line eqeqeq
                if ( b == document || b.ownerDocument == preferredDoc &&
                    contains( preferredDoc, b ) ) {
                    return 1;
                }
    
                // Maintain original order
                return sortInput ?
                    ( indexOf( sortInput, a ) - indexOf( sortInput, b ) ) :
                    0;
            }
    
            return compare & 4 ? -1 : 1;
        } :
        function( a, b ) {
    
            // Exit early if the nodes are identical
            if ( a === b ) {
                hasDuplicate = true;
                return 0;
            }
    
            var cur,
                i = 0,
                aup = a.parentNode,
                bup = b.parentNode,
                ap = [ a ],
                bp = [ b ];
    
            // Parentless nodes are either documents or disconnected
            if ( !aup || !bup ) {
    
                // Support: IE 11+, Edge 17 - 18+
                // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
                // two documents; shallow comparisons work.
                /* eslint-disable eqeqeq */
                return a == document ? -1 :
                    b == document ? 1 :
                    /* eslint-enable eqeqeq */
                    aup ? -1 :
                    bup ? 1 :
                    sortInput ?
                    ( indexOf( sortInput, a ) - indexOf( sortInput, b ) ) :
                    0;
    
            // If the nodes are siblings, we can do a quick check
            } else if ( aup === bup ) {
                return siblingCheck( a, b );
            }
    
            // Otherwise we need full lists of their ancestors for comparison
            cur = a;
            while ( ( cur = cur.parentNode ) ) {
                ap.unshift( cur );
            }
            cur = b;
            while ( ( cur = cur.parentNode ) ) {
                bp.unshift( cur );
            }
    
            // Walk down the tree looking for a discrepancy
            while ( ap[ i ] === bp[ i ] ) {
                i++;
            }
    
            return i ?
    
                // Do a sibling check if the nodes have a common ancestor
                siblingCheck( ap[ i ], bp[ i ] ) :
    
                // Otherwise nodes in our document sort first
                // Support: IE 11+, Edge 17 - 18+
                // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
                // two documents; shallow comparisons work.
                /* eslint-disable eqeqeq */
                ap[ i ] == preferredDoc ? -1 :
                bp[ i ] == preferredDoc ? 1 :
                /* eslint-enable eqeqeq */
                0;
        };
    
        return document;
    };
    
    Sizzle.matches = function( expr, elements ) {
        return Sizzle( expr, null, null, elements );
    };
    
    Sizzle.matchesSelector = function( elem, expr ) {
        setDocument( elem );
    
        if ( support.matchesSelector && documentIsHTML &&
            !nonnativeSelectorCache[ expr + " " ] &&
            ( !rbuggyMatches || !rbuggyMatches.test( expr ) ) &&
            ( !rbuggyQSA     || !rbuggyQSA.test( expr ) ) ) {
    
            try {
                var ret = matches.call( elem, expr );
    
                // IE 9's matchesSelector returns false on disconnected nodes
                if ( ret || support.disconnectedMatch ||
    
                    // As well, disconnected nodes are said to be in a document
                    // fragment in IE 9
                    elem.document && elem.document.nodeType !== 11 ) {
                    return ret;
                }
            } catch ( e ) {
                nonnativeSelectorCache( expr, true );
            }
        }
    
        return Sizzle( expr, document, null, [ elem ] ).length > 0;
    };
    
    Sizzle.contains = function( context, elem ) {
    
        // Set document vars if needed
        // Support: IE 11+, Edge 17 - 18+
        // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
        // two documents; shallow comparisons work.
        // eslint-disable-next-line eqeqeq
        if ( ( context.ownerDocument || context ) != document ) {
            setDocument( context );
        }
        return contains( context, elem );
    };
    
    Sizzle.attr = function( elem, name ) {
    
        // Set document vars if needed
        // Support: IE 11+, Edge 17 - 18+
        // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
        // two documents; shallow comparisons work.
        // eslint-disable-next-line eqeqeq
        if ( ( elem.ownerDocument || elem ) != document ) {
            setDocument( elem );
        }
    
        var fn = Expr.attrHandle[ name.toLowerCase() ],
    
            // Don't get fooled by Object.prototype properties (jQuery #13807)
            val = fn && hasOwn.call( Expr.attrHandle, name.toLowerCase() ) ?
                fn( elem, name, !documentIsHTML ) :
                undefined;
    
        return val !== undefined ?
            val :
            support.attributes || !documentIsHTML ?
                elem.getAttribute( name ) :
                ( val = elem.getAttributeNode( name ) ) && val.specified ?
                    val.value :
                    null;
    };
    
    Sizzle.escape = function( sel ) {
        return ( sel + "" ).replace( rcssescape, fcssescape );
    };
    
    Sizzle.error = function( msg ) {
        throw new Error( "Syntax error, unrecognized expression: " + msg );
    };
    
    /**
     * Document sorting and removing duplicates
     * @param {ArrayLike} results
     */
    Sizzle.uniqueSort = function( results ) {
        var elem,
            duplicates = [],
            j = 0,
            i = 0;
    
        // Unless we *know* we can detect duplicates, assume their presence
        hasDuplicate = !support.detectDuplicates;
        sortInput = !support.sortStable && results.slice( 0 );
        results.sort( sortOrder );
    
        if ( hasDuplicate ) {
            while ( ( elem = results[ i++ ] ) ) {
                if ( elem === results[ i ] ) {
                    j = duplicates.push( i );
                }
            }
            while ( j-- ) {
                results.splice( duplicates[ j ], 1 );
            }
        }
    
        // Clear input after sorting to release objects
        // See https://github.com/jquery/sizzle/pull/225
        sortInput = null;
    
        return results;
    };
    
    /**
     * Utility function for retrieving the text value of an array of DOM nodes
     * @param {Array|Element} elem
     */
    getText = Sizzle.getText = function( elem ) {
        var node,
            ret = "",
            i = 0,
            nodeType = elem.nodeType;
    
        if ( !nodeType ) {
    
            // If no nodeType, this is expected to be an array
            while ( ( node = elem[ i++ ] ) ) {
    
                // Do not traverse comment nodes
                ret += getText( node );
            }
        } else if ( nodeType === 1 || nodeType === 9 || nodeType === 11 ) {
    
            // Use textContent for elements
            // innerText usage removed for consistency of new lines (jQuery #11153)
            if ( typeof elem.textContent === "string" ) {
                return elem.textContent;
            } else {
    
                // Traverse its children
                for ( elem = elem.firstChild; elem; elem = elem.nextSibling ) {
                    ret += getText( elem );
                }
            }
        } else if ( nodeType === 3 || nodeType === 4 ) {
            return elem.nodeValue;
        }
    
        // Do not include comment or processing instruction nodes
    
        return ret;
    };
    
    Expr = Sizzle.selectors = {
    
        // Can be adjusted by the user
        cacheLength: 50,
    
        createPseudo: markFunction,
    
        match: matchExpr,
    
        attrHandle: {},
    
        find: {},
    
        relative: {
            ">": { dir: "parentNode", first: true },
            " ": { dir: "parentNode" },
            "+": { dir: "previousSibling", first: true },
            "~": { dir: "previousSibling" }
        },
    
        preFilter: {
            "ATTR": function( match ) {
                match[ 1 ] = match[ 1 ].replace( runescape, funescape );
    
                // Move the given value to match[3] whether quoted or unquoted
                match[ 3 ] = ( match[ 3 ] || match[ 4 ] ||
                    match[ 5 ] || "" ).replace( runescape, funescape );
    
                if ( match[ 2 ] === "~=" ) {
                    match[ 3 ] = " " + match[ 3 ] + " ";
                }
    
                return match.slice( 0, 4 );
            },
    
            "CHILD": function( match ) {
    
                /* matches from matchExpr["CHILD"]
                    1 type (only|nth|...)
                    2 what (child|of-type)
                    3 argument (even|odd|\d*|\d*n([+-]\d+)?|...)
                    4 xn-component of xn+y argument ([+-]?\d*n|)
                    5 sign of xn-component
                    6 x of xn-component
                    7 sign of y-component
                    8 y of y-component
                */
                match[ 1 ] = match[ 1 ].toLowerCase();
    
                if ( match[ 1 ].slice( 0, 3 ) === "nth" ) {
    
                    // nth-* requires argument
                    if ( !match[ 3 ] ) {
                        Sizzle.error( match[ 0 ] );
                    }
    
                    // numeric x and y parameters for Expr.filter.CHILD
                    // remember that false/true cast respectively to 0/1
                    match[ 4 ] = +( match[ 4 ] ?
                        match[ 5 ] + ( match[ 6 ] || 1 ) :
                        2 * ( match[ 3 ] === "even" || match[ 3 ] === "odd" ) );
                    match[ 5 ] = +( ( match[ 7 ] + match[ 8 ] ) || match[ 3 ] === "odd" );
    
                    // other types prohibit arguments
                } else if ( match[ 3 ] ) {
                    Sizzle.error( match[ 0 ] );
                }
    
                return match;
            },
    
            "PSEUDO": function( match ) {
                var excess,
                    unquoted = !match[ 6 ] && match[ 2 ];
    
                if ( matchExpr[ "CHILD" ].test( match[ 0 ] ) ) {
                    return null;
                }
    
                // Accept quoted arguments as-is
                if ( match[ 3 ] ) {
                    match[ 2 ] = match[ 4 ] || match[ 5 ] || "";
    
                // Strip excess characters from unquoted arguments
                } else if ( unquoted && rpseudo.test( unquoted ) &&
    
                    // Get excess from tokenize (recursively)
                    ( excess = tokenize( unquoted, true ) ) &&
    
                    // advance to the next closing parenthesis
                    ( excess = unquoted.indexOf( ")", unquoted.length - excess ) - unquoted.length ) ) {
    
                    // excess is a negative index
                    match[ 0 ] = match[ 0 ].slice( 0, excess );
                    match[ 2 ] = unquoted.slice( 0, excess );
                }
    
                // Return only captures needed by the pseudo filter method (type and argument)
                return match.slice( 0, 3 );
            }
        },
    
        filter: {
    
            "TAG": function( nodeNameSelector ) {
                var nodeName = nodeNameSelector.replace( runescape, funescape ).toLowerCase();
                return nodeNameSelector === "*" ?
                    function() {
                        return true;
                    } :
                    function( elem ) {
                        return elem.nodeName && elem.nodeName.toLowerCase() === nodeName;
                    };
            },
    
            "CLASS": function( className ) {
                var pattern = classCache[ className + " " ];
    
                return pattern ||
                    ( pattern = new RegExp( "(^|" + whitespace +
                        ")" + className + "(" + whitespace + "|$)" ) ) && classCache(
                            className, function( elem ) {
                                return pattern.test(
                                    typeof elem.className === "string" && elem.className ||
                                    typeof elem.getAttribute !== "undefined" &&
                                        elem.getAttribute( "class" ) ||
                                    ""
                                );
                    } );
            },
    
            "ATTR": function( name, operator, check ) {
                return function( elem ) {
                    var result = Sizzle.attr( elem, name );
    
                    if ( result == null ) {
                        return operator === "!=";
                    }
                    if ( !operator ) {
                        return true;
                    }
    
                    result += "";
    
                    /* eslint-disable max-len */
    
                    return operator === "=" ? result === check :
                        operator === "!=" ? result !== check :
                        operator === "^=" ? check && result.indexOf( check ) === 0 :
                        operator === "*=" ? check && result.indexOf( check ) > -1 :
                        operator === "$=" ? check && result.slice( -check.length ) === check :
                        operator === "~=" ? ( " " + result.replace( rwhitespace, " " ) + " " ).indexOf( check ) > -1 :
                        operator === "|=" ? result === check || result.slice( 0, check.length + 1 ) === check + "-" :
                        false;
                    /* eslint-enable max-len */
    
                };
            },
    
            "CHILD": function( type, what, _argument, first, last ) {
                var simple = type.slice( 0, 3 ) !== "nth",
                    forward = type.slice( -4 ) !== "last",
                    ofType = what === "of-type";
    
                return first === 1 && last === 0 ?
    
                    // Shortcut for :nth-*(n)
                    function( elem ) {
                        return !!elem.parentNode;
                    } :
    
                    function( elem, _context, xml ) {
                        var cache, uniqueCache, outerCache, node, nodeIndex, start,
                            dir = simple !== forward ? "nextSibling" : "previousSibling",
                            parent = elem.parentNode,
                            name = ofType && elem.nodeName.toLowerCase(),
                            useCache = !xml && !ofType,
                            diff = false;
    
                        if ( parent ) {
    
                            // :(first|last|only)-(child|of-type)
                            if ( simple ) {
                                while ( dir ) {
                                    node = elem;
                                    while ( ( node = node[ dir ] ) ) {
                                        if ( ofType ?
                                            node.nodeName.toLowerCase() === name :
                                            node.nodeType === 1 ) {
    
                                            return false;
                                        }
                                    }
    
                                    // Reverse direction for :only-* (if we haven't yet done so)
                                    start = dir = type === "only" && !start && "nextSibling";
                                }
                                return true;
                            }
    
                            start = [ forward ? parent.firstChild : parent.lastChild ];
    
                            // non-xml :nth-child(...) stores cache data on `parent`
                            if ( forward && useCache ) {
    
                                // Seek `elem` from a previously-cached index
    
                                // ...in a gzip-friendly way
                                node = parent;
                                outerCache = node[ expando ] || ( node[ expando ] = {} );
    
                                // Support: IE <9 only
                                // Defend against cloned attroperties (jQuery gh-1709)
                                uniqueCache = outerCache[ node.uniqueID ] ||
                                    ( outerCache[ node.uniqueID ] = {} );
    
                                cache = uniqueCache[ type ] || [];
                                nodeIndex = cache[ 0 ] === dirruns && cache[ 1 ];
                                diff = nodeIndex && cache[ 2 ];
                                node = nodeIndex && parent.childNodes[ nodeIndex ];
    
                                while ( ( node = ++nodeIndex && node && node[ dir ] ||
    
                                    // Fallback to seeking `elem` from the start
                                    ( diff = nodeIndex = 0 ) || start.pop() ) ) {
    
                                    // When found, cache indexes on `parent` and break
                                    if ( node.nodeType === 1 && ++diff && node === elem ) {
                                        uniqueCache[ type ] = [ dirruns, nodeIndex, diff ];
                                        break;
                                    }
                                }
    
                            } else {
    
                                // Use previously-cached element index if available
                                if ( useCache ) {
    
                                    // ...in a gzip-friendly way
                                    node = elem;
                                    outerCache = node[ expando ] || ( node[ expando ] = {} );
    
                                    // Support: IE <9 only
                                    // Defend against cloned attroperties (jQuery gh-1709)
                                    uniqueCache = outerCache[ node.uniqueID ] ||
                                        ( outerCache[ node.uniqueID ] = {} );
    
                                    cache = uniqueCache[ type ] || [];
                                    nodeIndex = cache[ 0 ] === dirruns && cache[ 1 ];
                                    diff = nodeIndex;
                                }
    
                                // xml :nth-child(...)
                                // or :nth-last-child(...) or :nth(-last)?-of-type(...)
                                if ( diff === false ) {
    
                                    // Use the same loop as above to seek `elem` from the start
                                    while ( ( node = ++nodeIndex && node && node[ dir ] ||
                                        ( diff = nodeIndex = 0 ) || start.pop() ) ) {
    
                                        if ( ( ofType ?
                                            node.nodeName.toLowerCase() === name :
                                            node.nodeType === 1 ) &&
                                            ++diff ) {
    
                                            // Cache the index of each encountered element
                                            if ( useCache ) {
                                                outerCache = node[ expando ] ||
                                                    ( node[ expando ] = {} );
    
                                                // Support: IE <9 only
                                                // Defend against cloned attroperties (jQuery gh-1709)
                                                uniqueCache = outerCache[ node.uniqueID ] ||
                                                    ( outerCache[ node.uniqueID ] = {} );
    
                                                uniqueCache[ type ] = [ dirruns, diff ];
                                            }
    
                                            if ( node === elem ) {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
    
                            // Incorporate the offset, then check against cycle size
                            diff -= last;
                            return diff === first || ( diff % first === 0 && diff / first >= 0 );
                        }
                    };
            },
    
            "PSEUDO": function( pseudo, argument ) {
    
                // pseudo-class names are case-insensitive
                // http://www.w3.org/TR/selectors/#pseudo-classes
                // Prioritize by case sensitivity in case custom pseudos are added with uppercase letters
                // Remember that setFilters inherits from pseudos
                var args,
                    fn = Expr.pseudos[ pseudo ] || Expr.setFilters[ pseudo.toLowerCase() ] ||
                        Sizzle.error( "unsupported pseudo: " + pseudo );
    
                // The user may use createPseudo to indicate that
                // arguments are needed to create the filter function
                // just as Sizzle does
                if ( fn[ expando ] ) {
                    return fn( argument );
                }
    
                // But maintain support for old signatures
                if ( fn.length > 1 ) {
                    args = [ pseudo, pseudo, "", argument ];
                    return Expr.setFilters.hasOwnProperty( pseudo.toLowerCase() ) ?
                        markFunction( function( seed, matches ) {
                            var idx,
                                matched = fn( seed, argument ),
                                i = matched.length;
                            while ( i-- ) {
                                idx = indexOf( seed, matched[ i ] );
                                seed[ idx ] = !( matches[ idx ] = matched[ i ] );
                            }
                        } ) :
                        function( elem ) {
                            return fn( elem, 0, args );
                        };
                }
    
                return fn;
            }
        },
    
        pseudos: {
    
            // Potentially complex pseudos
            "not": markFunction( function( selector ) {
    
                // Trim the selector passed to compile
                // to avoid treating leading and trailing
                // spaces as combinators
                var input = [],
                    results = [],
                    matcher = compile( selector.replace( rtrim, "$1" ) );
    
                return matcher[ expando ] ?
                    markFunction( function( seed, matches, _context, xml ) {
                        var elem,
                            unmatched = matcher( seed, null, xml, [] ),
                            i = seed.length;
    
                        // Match elements unmatched by `matcher`
                        while ( i-- ) {
                            if ( ( elem = unmatched[ i ] ) ) {
                                seed[ i ] = !( matches[ i ] = elem );
                            }
                        }
                    } ) :
                    function( elem, _context, xml ) {
                        input[ 0 ] = elem;
                        matcher( input, null, xml, results );
    
                        // Don't keep the element (issue #299)
                        input[ 0 ] = null;
                        return !results.pop();
                    };
            } ),
    
            "has": markFunction( function( selector ) {
                return function( elem ) {
                    return Sizzle( selector, elem ).length > 0;
                };
            } ),
    
            "contains": markFunction( function( text ) {
                text = text.replace( runescape, funescape );
                return function( elem ) {
                    return ( elem.textContent || getText( elem ) ).indexOf( text ) > -1;
                };
            } ),
    
            // "Whether an element is represented by a :lang() selector
            // is based solely on the element's language value
            // being equal to the identifier C,
            // or beginning with the identifier C immediately followed by "-".
            // The matching of C against the element's language value is performed case-insensitively.
            // The identifier C does not have to be a valid language name."
            // http://www.w3.org/TR/selectors/#lang-pseudo
            "lang": markFunction( function( lang ) {
    
                // lang value must be a valid identifier
                if ( !ridentifier.test( lang || "" ) ) {
                    Sizzle.error( "unsupported lang: " + lang );
                }
                lang = lang.replace( runescape, funescape ).toLowerCase();
                return function( elem ) {
                    var elemLang;
                    do {
                        if ( ( elemLang = documentIsHTML ?
                            elem.lang :
                            elem.getAttribute( "xml:lang" ) || elem.getAttribute( "lang" ) ) ) {
    
                            elemLang = elemLang.toLowerCase();
                            return elemLang === lang || elemLang.indexOf( lang + "-" ) === 0;
                        }
                    } while ( ( elem = elem.parentNode ) && elem.nodeType === 1 );
                    return false;
                };
            } ),
    
            // Miscellaneous
            "target": function( elem ) {
                var hash = window.location && window.location.hash;
                return hash && hash.slice( 1 ) === elem.id;
            },
    
            "root": function( elem ) {
                return elem === docElem;
            },
    
            "focus": function( elem ) {
                return elem === document.activeElement &&
                    ( !document.hasFocus || document.hasFocus() ) &&
                    !!( elem.type || elem.href || ~elem.tabIndex );
            },
    
            // Boolean properties
            "enabled": createDisabledPseudo( false ),
            "disabled": createDisabledPseudo( true ),
    
            "checked": function( elem ) {
    
                // In CSS3, :checked should return both checked and selected elements
                // http://www.w3.org/TR/2011/REC-css3-selectors-20110929/#checked
                var nodeName = elem.nodeName.toLowerCase();
                return ( nodeName === "input" && !!elem.checked ) ||
                    ( nodeName === "option" && !!elem.selected );
            },
    
            "selected": function( elem ) {
    
                // Accessing this property makes selected-by-default
                // options in Safari work properly
                if ( elem.parentNode ) {
                    // eslint-disable-next-line no-unused-expressions
                    elem.parentNode.selectedIndex;
                }
    
                return elem.selected === true;
            },
    
            // Contents
            "empty": function( elem ) {
    
                // http://www.w3.org/TR/selectors/#empty-pseudo
                // :empty is negated by element (1) or content nodes (text: 3; cdata: 4; entity ref: 5),
                //   but not by others (comment: 8; processing instruction: 7; etc.)
                // nodeType < 6 works because attributes (2) do not appear as children
                for ( elem = elem.firstChild; elem; elem = elem.nextSibling ) {
                    if ( elem.nodeType < 6 ) {
                        return false;
                    }
                }
                return true;
            },
    
            "parent": function( elem ) {
                return !Expr.pseudos[ "empty" ]( elem );
            },
    
            // Element/input types
            "header": function( elem ) {
                return rheader.test( elem.nodeName );
            },
    
            "input": function( elem ) {
                return rinputs.test( elem.nodeName );
            },
    
            "button": function( elem ) {
                var name = elem.nodeName.toLowerCase();
                return name === "input" && elem.type === "button" || name === "button";
            },
    
            "text": function( elem ) {
                var attr;
                return elem.nodeName.toLowerCase() === "input" &&
                    elem.type === "text" &&
    
                    // Support: IE<8
                    // New HTML5 attribute values (e.g., "search") appear with elem.type === "text"
                    ( ( attr = elem.getAttribute( "type" ) ) == null ||
                        attr.toLowerCase() === "text" );
            },
    
            // Position-in-collection
            "first": createPositionalPseudo( function() {
                return [ 0 ];
            } ),
    
            "last": createPositionalPseudo( function( _matchIndexes, length ) {
                return [ length - 1 ];
            } ),
    
            "eq": createPositionalPseudo( function( _matchIndexes, length, argument ) {
                return [ argument < 0 ? argument + length : argument ];
            } ),
    
            "even": createPositionalPseudo( function( matchIndexes, length ) {
                var i = 0;
                for ( ; i < length; i += 2 ) {
                    matchIndexes.push( i );
                }
                return matchIndexes;
            } ),
    
            "odd": createPositionalPseudo( function( matchIndexes, length ) {
                var i = 1;
                for ( ; i < length; i += 2 ) {
                    matchIndexes.push( i );
                }
                return matchIndexes;
            } ),
    
            "lt": createPositionalPseudo( function( matchIndexes, length, argument ) {
                var i = argument < 0 ?
                    argument + length :
                    argument > length ?
                        length :
                        argument;
                for ( ; --i >= 0; ) {
                    matchIndexes.push( i );
                }
                return matchIndexes;
            } ),
    
            "gt": createPositionalPseudo( function( matchIndexes, length, argument ) {
                var i = argument < 0 ? argument + length : argument;
                for ( ; ++i < length; ) {
                    matchIndexes.push( i );
                }
                return matchIndexes;
            } )
        }
    };
    
    Expr.pseudos[ "nth" ] = Expr.pseudos[ "eq" ];
    
    // Add button/input type pseudos
    for ( i in { radio: true, checkbox: true, file: true, password: true, image: true } ) {
        Expr.pseudos[ i ] = createInputPseudo( i );
    }
    for ( i in { submit: true, reset: true } ) {
        Expr.pseudos[ i ] = createButtonPseudo( i );
    }
    
    // Easy API for creating new setFilters
    function setFilters() {}
    setFilters.prototype = Expr.filters = Expr.pseudos;
    Expr.setFilters = new setFilters();
    
    tokenize = Sizzle.tokenize = function( selector, parseOnly ) {
        var matched, match, tokens, type,
            soFar, groups, preFilters,
            cached = tokenCache[ selector + " " ];
    
        if ( cached ) {
            return parseOnly ? 0 : cached.slice( 0 );
        }
    
        soFar = selector;
        groups = [];
        preFilters = Expr.preFilter;
    
        while ( soFar ) {
    
            // Comma and first run
            if ( !matched || ( match = rcomma.exec( soFar ) ) ) {
                if ( match ) {
    
                    // Don't consume trailing commas as valid
                    soFar = soFar.slice( match[ 0 ].length ) || soFar;
                }
                groups.push( ( tokens = [] ) );
            }
    
            matched = false;
    
            // Combinators
            if ( ( match = rcombinators.exec( soFar ) ) ) {
                matched = match.shift();
                tokens.push( {
                    value: matched,
    
                    // Cast descendant combinators to space
                    type: match[ 0 ].replace( rtrim, " " )
                } );
                soFar = soFar.slice( matched.length );
            }
    
            // Filters
            for ( type in Expr.filter ) {
                if ( ( match = matchExpr[ type ].exec( soFar ) ) && ( !preFilters[ type ] ||
                    ( match = preFilters[ type ]( match ) ) ) ) {
                    matched = match.shift();
                    tokens.push( {
                        value: matched,
                        type: type,
                        matches: match
                    } );
                    soFar = soFar.slice( matched.length );
                }
            }
    
            if ( !matched ) {
                break;
            }
        }
    
        // Return the length of the invalid excess
        // if we're just parsing
        // Otherwise, throw an error or return tokens
        return parseOnly ?
            soFar.length :
            soFar ?
                Sizzle.error( selector ) :
    
                // Cache the tokens
                tokenCache( selector, groups ).slice( 0 );
    };
    
    function toSelector( tokens ) {
        var i = 0,
            len = tokens.length,
            selector = "";
        for ( ; i < len; i++ ) {
            selector += tokens[ i ].value;
        }
        return selector;
    }
    
    function addCombinator( matcher, combinator, base ) {
        var dir = combinator.dir,
            skip = combinator.next,
            key = skip || dir,
            checkNonElements = base && key === "parentNode",
            doneName = done++;
    
        return combinator.first ?
    
            // Check against closest ancestor/preceding element
            function( elem, context, xml ) {
                while ( ( elem = elem[ dir ] ) ) {
                    if ( elem.nodeType === 1 || checkNonElements ) {
                        return matcher( elem, context, xml );
                    }
                }
                return false;
            } :
    
            // Check against all ancestor/preceding elements
            function( elem, context, xml ) {
                var oldCache, uniqueCache, outerCache,
                    newCache = [ dirruns, doneName ];
    
                // We can't set arbitrary data on XML nodes, so they don't benefit from combinator caching
                if ( xml ) {
                    while ( ( elem = elem[ dir ] ) ) {
                        if ( elem.nodeType === 1 || checkNonElements ) {
                            if ( matcher( elem, context, xml ) ) {
                                return true;
                            }
                        }
                    }
                } else {
                    while ( ( elem = elem[ dir ] ) ) {
                        if ( elem.nodeType === 1 || checkNonElements ) {
                            outerCache = elem[ expando ] || ( elem[ expando ] = {} );
    
                            // Support: IE <9 only
                            // Defend against cloned attroperties (jQuery gh-1709)
                            uniqueCache = outerCache[ elem.uniqueID ] ||
                                ( outerCache[ elem.uniqueID ] = {} );
    
                            if ( skip && skip === elem.nodeName.toLowerCase() ) {
                                elem = elem[ dir ] || elem;
                            } else if ( ( oldCache = uniqueCache[ key ] ) &&
                                oldCache[ 0 ] === dirruns && oldCache[ 1 ] === doneName ) {
    
                                // Assign to newCache so results back-propagate to previous elements
                                return ( newCache[ 2 ] = oldCache[ 2 ] );
                            } else {
    
                                // Reuse newcache so results back-propagate to previous elements
                                uniqueCache[ key ] = newCache;
    
                                // A match means we're done; a fail means we have to keep checking
                                if ( ( newCache[ 2 ] = matcher( elem, context, xml ) ) ) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                return false;
            };
    }
    
    function elementMatcher( matchers ) {
        return matchers.length > 1 ?
            function( elem, context, xml ) {
                var i = matchers.length;
                while ( i-- ) {
                    if ( !matchers[ i ]( elem, context, xml ) ) {
                        return false;
                    }
                }
                return true;
            } :
            matchers[ 0 ];
    }
    
    function multipleContexts( selector, contexts, results ) {
        var i = 0,
            len = contexts.length;
        for ( ; i < len; i++ ) {
            Sizzle( selector, contexts[ i ], results );
        }
        return results;
    }
    
    function condense( unmatched, map, filter, context, xml ) {
        var elem,
            newUnmatched = [],
            i = 0,
            len = unmatched.length,
            mapped = map != null;
    
        for ( ; i < len; i++ ) {
            if ( ( elem = unmatched[ i ] ) ) {
                if ( !filter || filter( elem, context, xml ) ) {
                    newUnmatched.push( elem );
                    if ( mapped ) {
                        map.push( i );
                    }
                }
            }
        }
    
        return newUnmatched;
    }
    
    function setMatcher( preFilter, selector, matcher, postFilter, postFinder, postSelector ) {
        if ( postFilter && !postFilter[ expando ] ) {
            postFilter = setMatcher( postFilter );
        }
        if ( postFinder && !postFinder[ expando ] ) {
            postFinder = setMatcher( postFinder, postSelector );
        }
        return markFunction( function( seed, results, context, xml ) {
            var temp, i, elem,
                preMap = [],
                postMap = [],
                preexisting = results.length,
    
                // Get initial elements from seed or context
                elems = seed || multipleContexts(
                    selector || "*",
                    context.nodeType ? [ context ] : context,
                    []
                ),
    
                // Prefilter to get matcher input, preserving a map for seed-results synchronization
                matcherIn = preFilter && ( seed || !selector ) ?
                    condense( elems, preMap, preFilter, context, xml ) :
                    elems,
    
                matcherOut = matcher ?
    
                    // If we have a postFinder, or filtered seed, or non-seed postFilter or preexisting results,
                    postFinder || ( seed ? preFilter : preexisting || postFilter ) ?
    
                        // ...intermediate processing is necessary
                        [] :
    
                        // ...otherwise use results directly
                        results :
                    matcherIn;
    
            // Find primary matches
            if ( matcher ) {
                matcher( matcherIn, matcherOut, context, xml );
            }
    
            // Apply postFilter
            if ( postFilter ) {
                temp = condense( matcherOut, postMap );
                postFilter( temp, [], context, xml );
    
                // Un-match failing elements by moving them back to matcherIn
                i = temp.length;
                while ( i-- ) {
                    if ( ( elem = temp[ i ] ) ) {
                        matcherOut[ postMap[ i ] ] = !( matcherIn[ postMap[ i ] ] = elem );
                    }
                }
            }
    
            if ( seed ) {
                if ( postFinder || preFilter ) {
                    if ( postFinder ) {
    
                        // Get the final matcherOut by condensing this intermediate into postFinder contexts
                        temp = [];
                        i = matcherOut.length;
                        while ( i-- ) {
                            if ( ( elem = matcherOut[ i ] ) ) {
    
                                // Restore matcherIn since elem is not yet a final match
                                temp.push( ( matcherIn[ i ] = elem ) );
                            }
                        }
                        postFinder( null, ( matcherOut = [] ), temp, xml );
                    }
    
                    // Move matched elements from seed to results to keep them synchronized
                    i = matcherOut.length;
                    while ( i-- ) {
                        if ( ( elem = matcherOut[ i ] ) &&
                            ( temp = postFinder ? indexOf( seed, elem ) : preMap[ i ] ) > -1 ) {
    
                            seed[ temp ] = !( results[ temp ] = elem );
                        }
                    }
                }
    
            // Add elements to results, through postFinder if defined
            } else {
                matcherOut = condense(
                    matcherOut === results ?
                        matcherOut.splice( preexisting, matcherOut.length ) :
                        matcherOut
                );
                if ( postFinder ) {
                    postFinder( null, results, matcherOut, xml );
                } else {
                    push.apply( results, matcherOut );
                }
            }
        } );
    }
    
    function matcherFromTokens( tokens ) {
        var checkContext, matcher, j,
            len = tokens.length,
            leadingRelative = Expr.relative[ tokens[ 0 ].type ],
            implicitRelative = leadingRelative || Expr.relative[ " " ],
            i = leadingRelative ? 1 : 0,
    
            // The foundational matcher ensures that elements are reachable from top-level context(s)
            matchContext = addCombinator( function( elem ) {
                return elem === checkContext;
            }, implicitRelative, true ),
            matchAnyContext = addCombinator( function( elem ) {
                return indexOf( checkContext, elem ) > -1;
            }, implicitRelative, true ),
            matchers = [ function( elem, context, xml ) {
                var ret = ( !leadingRelative && ( xml || context !== outermostContext ) ) || (
                    ( checkContext = context ).nodeType ?
                        matchContext( elem, context, xml ) :
                        matchAnyContext( elem, context, xml ) );
    
                // Avoid hanging onto element (issue #299)
                checkContext = null;
                return ret;
            } ];
    
        for ( ; i < len; i++ ) {
            if ( ( matcher = Expr.relative[ tokens[ i ].type ] ) ) {
                matchers = [ addCombinator( elementMatcher( matchers ), matcher ) ];
            } else {
                matcher = Expr.filter[ tokens[ i ].type ].apply( null, tokens[ i ].matches );
    
                // Return special upon seeing a positional matcher
                if ( matcher[ expando ] ) {
    
                    // Find the next relative operator (if any) for proper handling
                    j = ++i;
                    for ( ; j < len; j++ ) {
                        if ( Expr.relative[ tokens[ j ].type ] ) {
                            break;
                        }
                    }
                    return setMatcher(
                        i > 1 && elementMatcher( matchers ),
                        i > 1 && toSelector(
    
                        // If the preceding token was a descendant combinator, insert an implicit any-element `*`
                        tokens
                            .slice( 0, i - 1 )
                            .concat( { value: tokens[ i - 2 ].type === " " ? "*" : "" } )
                        ).replace( rtrim, "$1" ),
                        matcher,
                        i < j && matcherFromTokens( tokens.slice( i, j ) ),
                        j < len && matcherFromTokens( ( tokens = tokens.slice( j ) ) ),
                        j < len && toSelector( tokens )
                    );
                }
                matchers.push( matcher );
            }
        }
    
        return elementMatcher( matchers );
    }
    
    function matcherFromGroupMatchers( elementMatchers, setMatchers ) {
        var bySet = setMatchers.length > 0,
            byElement = elementMatchers.length > 0,
            superMatcher = function( seed, context, xml, results, outermost ) {
                var elem, j, matcher,
                    matchedCount = 0,
                    i = "0",
                    unmatched = seed && [],
                    setMatched = [],
                    contextBackup = outermostContext,
    
                    // We must always have either seed elements or outermost context
                    elems = seed || byElement && Expr.find[ "TAG" ]( "*", outermost ),
    
                    // Use integer dirruns iff this is the outermost matcher
                    dirrunsUnique = ( dirruns += contextBackup == null ? 1 : Math.random() || 0.1 ),
                    len = elems.length;
    
                if ( outermost ) {
    
                    // Support: IE 11+, Edge 17 - 18+
                    // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
                    // two documents; shallow comparisons work.
                    // eslint-disable-next-line eqeqeq
                    outermostContext = context == document || context || outermost;
                }
    
                // Add elements passing elementMatchers directly to results
                // Support: IE<9, Safari
                // Tolerate NodeList properties (IE: "length"; Safari: <number>) matching elements by id
                for ( ; i !== len && ( elem = elems[ i ] ) != null; i++ ) {
                    if ( byElement && elem ) {
                        j = 0;
    
                        // Support: IE 11+, Edge 17 - 18+
                        // IE/Edge sometimes throw a "Permission denied" error when strict-comparing
                        // two documents; shallow comparisons work.
                        // eslint-disable-next-line eqeqeq
                        if ( !context && elem.ownerDocument != document ) {
                            setDocument( elem );
                            xml = !documentIsHTML;
                        }
                        while ( ( matcher = elementMatchers[ j++ ] ) ) {
                            if ( matcher( elem, context || document, xml ) ) {
                                results.push( elem );
                                break;
                            }
                        }
                        if ( outermost ) {
                            dirruns = dirrunsUnique;
                        }
                    }
    
                    // Track unmatched elements for set filters
                    if ( bySet ) {
    
                        // They will have gone through all possible matchers
                        if ( ( elem = !matcher && elem ) ) {
                            matchedCount--;
                        }
    
                        // Lengthen the array for every element, matched or not
                        if ( seed ) {
                            unmatched.push( elem );
                        }
                    }
                }
    
                // `i` is now the count of elements visited above, and adding it to `matchedCount`
                // makes the latter nonnegative.
                matchedCount += i;
    
                // Apply set filters to unmatched elements
                // NOTE: This can be skipped if there are no unmatched elements (i.e., `matchedCount`
                // equals `i`), unless we didn't visit _any_ elements in the above loop because we have
                // no element matchers and no seed.
                // Incrementing an initially-string "0" `i` allows `i` to remain a string only in that
                // case, which will result in a "00" `matchedCount` that differs from `i` but is also
                // numerically zero.
                if ( bySet && i !== matchedCount ) {
                    j = 0;
                    while ( ( matcher = setMatchers[ j++ ] ) ) {
                        matcher( unmatched, setMatched, context, xml );
                    }
    
                    if ( seed ) {
    
                        // Reintegrate element matches to eliminate the need for sorting
                        if ( matchedCount > 0 ) {
                            while ( i-- ) {
                                if ( !( unmatched[ i ] || setMatched[ i ] ) ) {
                                    setMatched[ i ] = pop.call( results );
                                }
                            }
                        }
    
                        // Discard index placeholder values to get only actual matches
                        setMatched = condense( setMatched );
                    }
    
                    // Add matches to results
                    push.apply( results, setMatched );
    
                    // Seedless set matches succeeding multiple successful matchers stipulate sorting
                    if ( outermost && !seed && setMatched.length > 0 &&
                        ( matchedCount + setMatchers.length ) > 1 ) {
    
                        Sizzle.uniqueSort( results );
                    }
                }
    
                // Override manipulation of globals by nested matchers
                if ( outermost ) {
                    dirruns = dirrunsUnique;
                    outermostContext = contextBackup;
                }
    
                return unmatched;
            };
    
        return bySet ?
            markFunction( superMatcher ) :
            superMatcher;
    }
    
    compile = Sizzle.compile = function( selector, match /* Internal Use Only */ ) {
        var i,
            setMatchers = [],
            elementMatchers = [],
            cached = compilerCache[ selector + " " ];
    
        if ( !cached ) {
    
            // Generate a function of recursive functions that can be used to check each element
            if ( !match ) {
                match = tokenize( selector );
            }
            i = match.length;
            while ( i-- ) {
                cached = matcherFromTokens( match[ i ] );
                if ( cached[ expando ] ) {
                    setMatchers.push( cached );
                } else {
                    elementMatchers.push( cached );
                }
            }
    
            // Cache the compiled function
            cached = compilerCache(
                selector,
                matcherFromGroupMatchers( elementMatchers, setMatchers )
            );
    
            // Save selector and tokenization
            cached.selector = selector;
        }
        return cached;
    };
    
    /**
     * A low-level selection function that works with Sizzle's compiled
     *  selector functions
     * @param {String|Function} selector A selector or a pre-compiled
     *  selector function built with Sizzle.compile
     * @param {Element} context
     * @param {Array} [results]
     * @param {Array} [seed] A set of elements to match against
     */
    select = Sizzle.select = function( selector, context, results, seed ) {
        var i, tokens, token, type, find,
            compiled = typeof selector === "function" && selector,
            match = !seed && tokenize( ( selector = compiled.selector || selector ) );
    
        results = results || [];
    
        // Try to minimize operations if there is only one selector in the list and no seed
        // (the latter of which guarantees us context)
        if ( match.length === 1 ) {
    
            // Reduce context if the leading compound selector is an ID
            tokens = match[ 0 ] = match[ 0 ].slice( 0 );
            if ( tokens.length > 2 && ( token = tokens[ 0 ] ).type === "ID" &&
                context.nodeType === 9 && documentIsHTML && Expr.relative[ tokens[ 1 ].type ] ) {
    
                context = ( Expr.find[ "ID" ]( token.matches[ 0 ]
                    .replace( runescape, funescape ), context ) || [] )[ 0 ];
                if ( !context ) {
                    return results;
    
                // Precompiled matchers will still verify ancestry, so step up a level
                } else if ( compiled ) {
                    context = context.parentNode;
                }
    
                selector = selector.slice( tokens.shift().value.length );
            }
    
            // Fetch a seed set for right-to-left matching
            i = matchExpr[ "needsContext" ].test( selector ) ? 0 : tokens.length;
            while ( i-- ) {
                token = tokens[ i ];
    
                // Abort if we hit a combinator
                if ( Expr.relative[ ( type = token.type ) ] ) {
                    break;
                }
                if ( ( find = Expr.find[ type ] ) ) {
    
                    // Search, expanding context for leading sibling combinators
                    if ( ( seed = find(
                        token.matches[ 0 ].replace( runescape, funescape ),
                        rsibling.test( tokens[ 0 ].type ) && testContext( context.parentNode ) ||
                            context
                    ) ) ) {
    
                        // If seed is empty or no tokens remain, we can return early
                        tokens.splice( i, 1 );
                        selector = seed.length && toSelector( tokens );
                        if ( !selector ) {
                            push.apply( results, seed );
                            return results;
                        }
    
                        break;
                    }
                }
            }
        }
    
        // Compile and execute a filtering function if one is not provided
        // Provide `match` to avoid retokenization if we modified the selector above
        ( compiled || compile( selector, match ) )(
            seed,
            context,
            !documentIsHTML,
            results,
            !context || rsibling.test( selector ) && testContext( context.parentNode ) || context
        );
        return results;
    };
    
    // One-time assignments
    
    // Sort stability
    support.sortStable = expando.split( "" ).sort( sortOrder ).join( "" ) === expando;
    
    // Support: Chrome 14-35+
    // Always assume duplicates if they aren't passed to the comparison function
    support.detectDuplicates = !!hasDuplicate;
    
    // Initialize against the default document
    setDocument();
    
    // Support: Webkit<537.32 - Safari 6.0.3/Chrome 25 (fixed in Chrome 27)
    // Detached nodes confoundingly follow *each other*
    support.sortDetached = assert( function( el ) {
    
        // Should return 1, but returns 4 (following)
        return el.compareDocumentPosition( document.createElement( "fieldset" ) ) & 1;
    } );
    
    // Support: IE<8
    // Prevent attribute/property "interpolation"
    // https://msdn.microsoft.com/en-us/library/ms536429%28VS.85%29.aspx
    if ( !assert( function( el ) {
        el.innerHTML = "<a href='#'></a>";
        return el.firstChild.getAttribute( "href" ) === "#";
    } ) ) {
        addHandle( "type|href|height|width", function( elem, name, isXML ) {
            if ( !isXML ) {
                return elem.getAttribute( name, name.toLowerCase() === "type" ? 1 : 2 );
            }
        } );
    }
    
    // Support: IE<9
    // Use defaultValue in place of getAttribute("value")
    if ( !support.attributes || !assert( function( el ) {
        el.innerHTML = "<input/>";
        el.firstChild.setAttribute( "value", "" );
        return el.firstChild.getAttribute( "value" ) === "";
    } ) ) {
        addHandle( "value", function( elem, _name, isXML ) {
            if ( !isXML && elem.nodeName.toLowerCase() === "input" ) {
                return elem.defaultValue;
            }
        } );
    }
    
    // Support: IE<9
    // Use getAttributeNode to fetch booleans when getAttribute lies
    if ( !assert( function( el ) {
        return el.getAttribute( "disabled" ) == null;
    } ) ) {
        addHandle( booleans, function( elem, name, isXML ) {
            var val;
            if ( !isXML ) {
                return elem[ name ] === true ? name.toLowerCase() :
                    ( val = elem.getAttributeNode( name ) ) && val.specified ?
                        val.value :
                        null;
            }
        } );
    }
    
    return Sizzle;
    
    } )( window );
    
    
    
    jQuery.find = Sizzle;
    jQuery.expr = Sizzle.selectors;
    
    // Deprecated
    jQuery.expr[ ":" ] = jQuery.expr.pseudos;
    jQuery.uniqueSort = jQuery.unique = Sizzle.uniqueSort;
    jQuery.text = Sizzle.getText;
    jQuery.isXMLDoc = Sizzle.isXML;
    jQuery.contains = Sizzle.contains;
    jQuery.escapeSelector = Sizzle.escape;
    
    
    
    
    var dir = function( elem, dir, until ) {
        var matched = [],
            truncate = until !== undefined;
    
        while ( ( elem = elem[ dir ] ) && elem.nodeType !== 9 ) {
            if ( elem.nodeType === 1 ) {
                if ( truncate && jQuery( elem ).is( until ) ) {
                    break;
                }
                matched.push( elem );
            }
        }
        return matched;
    };
    
    
    var siblings = function( n, elem ) {
        var matched = [];
    
        for ( ; n; n = n.nextSibling ) {
            if ( n.nodeType === 1 && n !== elem ) {
                matched.push( n );
            }
        }
    
        return matched;
    };
    
    
    var rneedsContext = jQuery.expr.match.needsContext;
    
    
    
    function nodeName( elem, name ) {
    
        return elem.nodeName && elem.nodeName.toLowerCase() === name.toLowerCase();
    
    }
    var rsingleTag = ( /^<([a-z][^\/\0>:\x20\t\r\n\f]*)[\x20\t\r\n\f]*\/?>(?:<\/\1>|)$/i );
    
    
    
    // Implement the identical functionality for filter and not
    function winnow( elements, qualifier, not ) {
        if ( isFunction( qualifier ) ) {
            return jQuery.grep( elements, function( elem, i ) {
                return !!qualifier.call( elem, i, elem ) !== not;
            } );
        }
    
        // Single element
        if ( qualifier.nodeType ) {
            return jQuery.grep( elements, function( elem ) {
                return ( elem === qualifier ) !== not;
            } );
        }
    
        // Arraylike of elements (jQuery, arguments, Array)
        if ( typeof qualifier !== "string" ) {
            return jQuery.grep( elements, function( elem ) {
                return ( indexOf.call( qualifier, elem ) > -1 ) !== not;
            } );
        }
    
        // Filtered directly for both simple and complex selectors
        return jQuery.filter( qualifier, elements, not );
    }
    
    jQuery.filter = function( expr, elems, not ) {
        var elem = elems[ 0 ];
    
        if ( not ) {
            expr = ":not(" + expr + ")";
        }
    
        if ( elems.length === 1 && elem.nodeType === 1 ) {
            return jQuery.find.matchesSelector( elem, expr ) ? [ elem ] : [];
        }
    
        return jQuery.find.matches( expr, jQuery.grep( elems, function( elem ) {
            return elem.nodeType === 1;
        } ) );
    };
    
    jQuery.fn.extend( {
        find: function( selector ) {
            var i, ret,
                len = this.length,
                self = this;
    
            if ( typeof selector !== "string" ) {
                return this.pushStack( jQuery( selector ).filter( function() {
                    for ( i = 0; i < len; i++ ) {
                        if ( jQuery.contains( self[ i ], this ) ) {
                            return true;
                        }
                    }
                } ) );
            }
    
            ret = this.pushStack( [] );
    
            for ( i = 0; i < len; i++ ) {
                jQuery.find( selector, self[ i ], ret );
            }
    
            return len > 1 ? jQuery.uniqueSort( ret ) : ret;
        },
        filter: function( selector ) {
            return this.pushStack( winnow( this, selector || [], false ) );
        },
        not: function( selector ) {
            return this.pushStack( winnow( this, selector || [], true ) );
        },
        is: function( selector ) {
            return !!winnow(
                this,
    
                // If this is a positional/relative selector, check membership in the returned set
                // so $("p:first").is("p:last") won't return true for a doc with two "p".
                typeof selector === "string" && rneedsContext.test( selector ) ?
                    jQuery( selector ) :
                    selector || [],
                false
            ).length;
        }
    } );
    
    
    // Initialize a jQuery object
    
    
    // A central reference to the root jQuery(document)
    var rootjQuery,
    
        // A simple way to check for HTML strings
        // Prioritize #id over <tag> to avoid XSS via location.hash (#9521)
        // Strict HTML recognition (#11290: must start with <)
        // Shortcut simple #id case for speed
        rquickExpr = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]+))$/,
    
        init = jQuery.fn.init = function( selector, context, root ) {
            var match, elem;
    
            // HANDLE: $(""), $(null), $(undefined), $(false)
            if ( !selector ) {
                return this;
            }
    
            // Method init() accepts an alternate rootjQuery
            // so migrate can support jQuery.sub (gh-2101)
            root = root || rootjQuery;
    
            // Handle HTML strings
            if ( typeof selector === "string" ) {
                if ( selector[ 0 ] === "<" &&
                    selector[ selector.length - 1 ] === ">" &&
                    selector.length >= 3 ) {
    
                    // Assume that strings that start and end with <> are HTML and skip the regex check
                    match = [ null, selector, null ];
    
                } else {
                    match = rquickExpr.exec( selector );
                }
    
                // Match html or make sure no context is specified for #id
                if ( match && ( match[ 1 ] || !context ) ) {
    
                    // HANDLE: $(html) -> $(array)
                    if ( match[ 1 ] ) {
                        context = context instanceof jQuery ? context[ 0 ] : context;
    
                        // Option to run scripts is true for back-compat
                        // Intentionally let the error be thrown if parseHTML is not present
                        jQuery.merge( this, jQuery.parseHTML(
                            match[ 1 ],
                            context && context.nodeType ? context.ownerDocument || context : document,
                            true
                        ) );
    
                        // HANDLE: $(html, props)
                        if ( rsingleTag.test( match[ 1 ] ) && jQuery.isPlainObject( context ) ) {
                            for ( match in context ) {
    
                                // Properties of context are called as methods if possible
                                if ( isFunction( this[ match ] ) ) {
                                    this[ match ]( context[ match ] );
    
                                // ...and otherwise set as attributes
                                } else {
                                    this.attr( match, context[ match ] );
                                }
                            }
                        }
    
                        return this;
    
                    // HANDLE: $(#id)
                    } else {
                        elem = document.getElementById( match[ 2 ] );
    
                        if ( elem ) {
    
                            // Inject the element directly into the jQuery object
                            this[ 0 ] = elem;
                            this.length = 1;
                        }
                        return this;
                    }
    
                // HANDLE: $(expr, $(...))
                } else if ( !context || context.jquery ) {
                    return ( context || root ).find( selector );
    
                // HANDLE: $(expr, context)
                // (which is just equivalent to: $(context).find(expr)
                } else {
                    return this.constructor( context ).find( selector );
                }
    
            // HANDLE: $(DOMElement)
            } else if ( selector.nodeType ) {
                this[ 0 ] = selector;
                this.length = 1;
                return this;
    
            // HANDLE: $(function)
            // Shortcut for document ready
            } else if ( isFunction( selector ) ) {
                return root.ready !== undefined ?
                    root.ready( selector ) :
    
                    // Execute immediately if ready is not present
                    selector( jQuery );
            }
    
            return jQuery.makeArray( selector, this );
        };
    
    // Give the init function the jQuery prototype for later instantiation
    init.prototype = jQuery.fn;
    
    // Initialize central reference
    rootjQuery = jQuery( document );
    
    
    var rparentsprev = /^(?:parents|prev(?:Until|All))/,
    
        // Methods guaranteed to produce a unique set when starting from a unique set
        guaranteedUnique = {
            children: true,
            contents: true,
            next: true,
            prev: true
        };
    
    jQuery.fn.extend( {
        has: function( target ) {
            var targets = jQuery( target, this ),
                l = targets.length;
    
            return this.filter( function() {
                var i = 0;
                for ( ; i < l; i++ ) {
                    if ( jQuery.contains( this, targets[ i ] ) ) {
                        return true;
                    }
                }
            } );
        },
    
        closest: function( selectors, context ) {
            var cur,
                i = 0,
                l = this.length,
                matched = [],
                targets = typeof selectors !== "string" && jQuery( selectors );
    
            // Positional selectors never match, since there's no _selection_ context
            if ( !rneedsContext.test( selectors ) ) {
                for ( ; i < l; i++ ) {
                    for ( cur = this[ i ]; cur && cur !== context; cur = cur.parentNode ) {
    
                        // Always skip document fragments
                        if ( cur.nodeType < 11 && ( targets ?
                            targets.index( cur ) > -1 :
    
                            // Don't pass non-elements to Sizzle
                            cur.nodeType === 1 &&
                                jQuery.find.matchesSelector( cur, selectors ) ) ) {
    
                            matched.push( cur );
                            break;
                        }
                    }
                }
            }
    
            return this.pushStack( matched.length > 1 ? jQuery.uniqueSort( matched ) : matched );
        },
    
        // Determine the position of an element within the set
        index: function( elem ) {
    
            // No argument, return index in parent
            if ( !elem ) {
                return ( this[ 0 ] && this[ 0 ].parentNode ) ? this.first().prevAll().length : -1;
            }
    
            // Index in selector
            if ( typeof elem === "string" ) {
                return indexOf.call( jQuery( elem ), this[ 0 ] );
            }
    
            // Locate the position of the desired element
            return indexOf.call( this,
    
                // If it receives a jQuery object, the first element is used
                elem.jquery ? elem[ 0 ] : elem
            );
        },
    
        add: function( selector, context ) {
            return this.pushStack(
                jQuery.uniqueSort(
                    jQuery.merge( this.get(), jQuery( selector, context ) )
                )
            );
        },
    
        addBack: function( selector ) {
            return this.add( selector == null ?
                this.prevObject : this.prevObject.filter( selector )
            );
        }
    } );
    
    function sibling( cur, dir ) {
        while ( ( cur = cur[ dir ] ) && cur.nodeType !== 1 ) {}
        return cur;
    }
    
    jQuery.each( {
        parent: function( elem ) {
            var parent = elem.parentNode;
            return parent && parent.nodeType !== 11 ? parent : null;
        },
        parents: function( elem ) {
            return dir( elem, "parentNode" );
        },
        parentsUntil: function( elem, _i, until ) {
            return dir( elem, "parentNode", until );
        },
        next: function( elem ) {
            return sibling( elem, "nextSibling" );
        },
        prev: function( elem ) {
            return sibling( elem, "previousSibling" );
        },
        nextAll: function( elem ) {
            return dir( elem, "nextSibling" );
        },
        prevAll: function( elem ) {
            return dir( elem, "previousSibling" );
        },
        nextUntil: function( elem, _i, until ) {
            return dir( elem, "nextSibling", until );
        },
        prevUntil: function( elem, _i, until ) {
            return dir( elem, "previousSibling", until );
        },
        siblings: function( elem ) {
            return siblings( ( elem.parentNode || {} ).firstChild, elem );
        },
        children: function( elem ) {
            return siblings( elem.firstChild );
        },
        contents: function( elem ) {
            if ( elem.contentDocument != null &&
    
                // Support: IE 11+
                // <object> elements with no `data` attribute has an object
                // `contentDocument` with a `null` prototype.
                getProto( elem.contentDocument ) ) {
    
                return elem.contentDocument;
            }
    
            // Support: IE 9 - 11 only, iOS 7 only, Android Browser <=4.3 only
            // Treat the template element as a regular one in browsers that
            // don't support it.
            if ( nodeName( elem, "template" ) ) {
                elem = elem.content || elem;
            }
    
            return jQuery.merge( [], elem.childNodes );
        }
    }, function( name, fn ) {
        jQuery.fn[ name ] = function( until, selector ) {
            var matched = jQuery.map( this, fn, until );
    
            if ( name.slice( -5 ) !== "Until" ) {
                selector = until;
            }
    
            if ( selector && typeof selector === "string" ) {
                matched = jQuery.filter( selector, matched );
            }
    
            if ( this.length > 1 ) {
    
                // Remove duplicates
                if ( !guaranteedUnique[ name ] ) {
                    jQuery.uniqueSort( matched );
                }
    
                // Reverse order for parents* and prev-derivatives
                if ( rparentsprev.test( name ) ) {
                    matched.reverse();
                }
            }
    
            return this.pushStack( matched );
        };
    } );
    var rnothtmlwhite = ( /[^\x20\t\r\n\f]+/g );
    
    
    
    // Convert String-formatted options into Object-formatted ones
    function createOptions( options ) {
        var object = {};
        jQuery.each( options.match( rnothtmlwhite ) || [], function( _, flag ) {
            object[ flag ] = true;
        } );
        return object;
    }
    
    /*
     * Create a callback list using the following parameters:
     *
     *	options: an optional list of space-separated options that will change how
     *			the callback list behaves or a more traditional option object
     *
     * By default a callback list will act like an event callback list and can be
     * "fired" multiple times.
     *
     * Possible options:
     *
     *	once:			will ensure the callback list can only be fired once (like a Deferred)
     *
     *	memory:			will keep track of previous values and will call any callback added
     *					after the list has been fired right away with the latest "memorized"
     *					values (like a Deferred)
     *
     *	unique:			will ensure a callback can only be added once (no duplicate in the list)
     *
     *	stopOnFalse:	interrupt callings when a callback returns false
     *
     */
    jQuery.Callbacks = function( options ) {
    
        // Convert options from String-formatted to Object-formatted if needed
        // (we check in cache first)
        options = typeof options === "string" ?
            createOptions( options ) :
            jQuery.extend( {}, options );
    
        var // Flag to know if list is currently firing
            firing,
    
            // Last fire value for non-forgettable lists
            memory,
    
            // Flag to know if list was already fired
            fired,
    
            // Flag to prevent firing
            locked,
    
            // Actual callback list
            list = [],
    
            // Queue of execution data for repeatable lists
            queue = [],
    
            // Index of currently firing callback (modified by add/remove as needed)
            firingIndex = -1,
    
            // Fire callbacks
            fire = function() {
    
                // Enforce single-firing
                locked = locked || options.once;
    
                // Execute callbacks for all pending executions,
                // respecting firingIndex overrides and runtime changes
                fired = firing = true;
                for ( ; queue.length; firingIndex = -1 ) {
                    memory = queue.shift();
                    while ( ++firingIndex < list.length ) {
    
                        // Run callback and check for early termination
                        if ( list[ firingIndex ].apply( memory[ 0 ], memory[ 1 ] ) === false &&
                            options.stopOnFalse ) {
    
                            // Jump to end and forget the data so .add doesn't re-fire
                            firingIndex = list.length;
                            memory = false;
                        }
                    }
                }
    
                // Forget the data if we're done with it
                if ( !options.memory ) {
                    memory = false;
                }
    
                firing = false;
    
                // Clean up if we're done firing for good
                if ( locked ) {
    
                    // Keep an empty list if we have data for future add calls
                    if ( memory ) {
                        list = [];
    
                    // Otherwise, this object is spent
                    } else {
                        list = "";
                    }
                }
            },
    
            // Actual Callbacks object
            self = {
    
                // Add a callback or a collection of callbacks to the list
                add: function() {
                    if ( list ) {
    
                        // If we have memory from a past run, we should fire after adding
                        if ( memory && !firing ) {
                            firingIndex = list.length - 1;
                            queue.push( memory );
                        }
    
                        ( function add( args ) {
                            jQuery.each( args, function( _, arg ) {
                                if ( isFunction( arg ) ) {
                                    if ( !options.unique || !self.has( arg ) ) {
                                        list.push( arg );
                                    }
                                } else if ( arg && arg.length && toType( arg ) !== "string" ) {
    
                                    // Inspect recursively
                                    add( arg );
                                }
                            } );
                        } )( arguments );
    
                        if ( memory && !firing ) {
                            fire();
                        }
                    }
                    return this;
                },
    
                // Remove a callback from the list
                remove: function() {
                    jQuery.each( arguments, function( _, arg ) {
                        var index;
                        while ( ( index = jQuery.inArray( arg, list, index ) ) > -1 ) {
                            list.splice( index, 1 );
    
                            // Handle firing indexes
                            if ( index <= firingIndex ) {
                                firingIndex--;
                            }
                        }
                    } );
                    return this;
                },
    
                // Check if a given callback is in the list.
                // If no argument is given, return whether or not list has callbacks attached.
                has: function( fn ) {
                    return fn ?
                        jQuery.inArray( fn, list ) > -1 :
                        list.length > 0;
                },
    
                // Remove all callbacks from the list
                empty: function() {
                    if ( list ) {
                        list = [];
                    }
                    return this;
                },
    
                // Disable .fire and .add
                // Abort any current/pending executions
                // Clear all callbacks and values
                disable: function() {
                    locked = queue = [];
                    list = memory = "";
                    return this;
                },
                disabled: function() {
                    return !list;
                },
    
                // Disable .fire
                // Also disable .add unless we have memory (since it would have no effect)
                // Abort any pending executions
                lock: function() {
                    locked = queue = [];
                    if ( !memory && !firing ) {
                        list = memory = "";
                    }
                    return this;
                },
                locked: function() {
                    return !!locked;
                },
    
                // Call all callbacks with the given context and arguments
                fireWith: function( context, args ) {
                    if ( !locked ) {
                        args = args || [];
                        args = [ context, args.slice ? args.slice() : args ];
                        queue.push( args );
                        if ( !firing ) {
                            fire();
                        }
                    }
                    return this;
                },
    
                // Call all the callbacks with the given arguments
                fire: function() {
                    self.fireWith( this, arguments );
                    return this;
                },
    
                // To know if the callbacks have already been called at least once
                fired: function() {
                    return !!fired;
                }
            };
    
        return self;
    };
    
    
    function Identity( v ) {
        return v;
    }
    function Thrower( ex ) {
        throw ex;
    }
    
    function adoptValue( value, resolve, reject, noValue ) {
        var method;
    
        try {
    
            // Check for promise aspect first to privilege synchronous behavior
            if ( value && isFunction( ( method = value.promise ) ) ) {
                method.call( value ).done( resolve ).fail( reject );
    
            // Other thenables
            } else if ( value && isFunction( ( method = value.then ) ) ) {
                method.call( value, resolve, reject );
    
            // Other non-thenables
            } else {
    
                // Control `resolve` arguments by letting Array#slice cast boolean `noValue` to integer:
                // * false: [ value ].slice( 0 ) => resolve( value )
                // * true: [ value ].slice( 1 ) => resolve()
                resolve.apply( undefined, [ value ].slice( noValue ) );
            }
    
        // For Promises/A+, convert exceptions into rejections
        // Since jQuery.when doesn't unwrap thenables, we can skip the extra checks appearing in
        // Deferred#then to conditionally suppress rejection.
        } catch ( value ) {
    
            // Support: Android 4.0 only
            // Strict mode functions invoked without .call/.apply get global-object context
            reject.apply( undefined, [ value ] );
        }
    }
    
    jQuery.extend( {
    
        Deferred: function( func ) {
            var tuples = [
    
                    // action, add listener, callbacks,
                    // ... .then handlers, argument index, [final state]
                    [ "notify", "progress", jQuery.Callbacks( "memory" ),
                        jQuery.Callbacks( "memory" ), 2 ],
                    [ "resolve", "done", jQuery.Callbacks( "once memory" ),
                        jQuery.Callbacks( "once memory" ), 0, "resolved" ],
                    [ "reject", "fail", jQuery.Callbacks( "once memory" ),
                        jQuery.Callbacks( "once memory" ), 1, "rejected" ]
                ],
                state = "pending",
                promise = {
                    state: function() {
                        return state;
                    },
                    always: function() {
                        deferred.done( arguments ).fail( arguments );
                        return this;
                    },
                    "catch": function( fn ) {
                        return promise.then( null, fn );
                    },
    
                    // Keep pipe for back-compat
                    pipe: function( /* fnDone, fnFail, fnProgress */ ) {
                        var fns = arguments;
    
                        return jQuery.Deferred( function( newDefer ) {
                            jQuery.each( tuples, function( _i, tuple ) {
    
                                // Map tuples (progress, done, fail) to arguments (done, fail, progress)
                                var fn = isFunction( fns[ tuple[ 4 ] ] ) && fns[ tuple[ 4 ] ];
    
                                // deferred.progress(function() { bind to newDefer or newDefer.notify })
                                // deferred.done(function() { bind to newDefer or newDefer.resolve })
                                // deferred.fail(function() { bind to newDefer or newDefer.reject })
                                deferred[ tuple[ 1 ] ]( function() {
                                    var returned = fn && fn.apply( this, arguments );
                                    if ( returned && isFunction( returned.promise ) ) {
                                        returned.promise()
                                            .progress( newDefer.notify )
                                            .done( newDefer.resolve )
                                            .fail( newDefer.reject );
                                    } else {
                                        newDefer[ tuple[ 0 ] + "With" ](
                                            this,
                                            fn ? [ returned ] : arguments
                                        );
                                    }
                                } );
                            } );
                            fns = null;
                        } ).promise();
                    },
                    then: function( onFulfilled, onRejected, onProgress ) {
                        var maxDepth = 0;
                        function resolve( depth, deferred, handler, special ) {
                            return function() {
                                var that = this,
                                    args = arguments,
                                    mightThrow = function() {
                                        var returned, then;
    
                                        // Support: Promises/A+ section 2.3.3.3.3
                                        // https://promisesaplus.com/#point-59
                                        // Ignore double-resolution attempts
                                        if ( depth < maxDepth ) {
                                            return;
                                        }
    
                                        returned = handler.apply( that, args );
    
                                        // Support: Promises/A+ section 2.3.1
                                        // https://promisesaplus.com/#point-48
                                        if ( returned === deferred.promise() ) {
                                            throw new TypeError( "Thenable self-resolution" );
                                        }
    
                                        // Support: Promises/A+ sections 2.3.3.1, 3.5
                                        // https://promisesaplus.com/#point-54
                                        // https://promisesaplus.com/#point-75
                                        // Retrieve `then` only once
                                        then = returned &&
    
                                            // Support: Promises/A+ section 2.3.4
                                            // https://promisesaplus.com/#point-64
                                            // Only check objects and functions for thenability
                                            ( typeof returned === "object" ||
                                                typeof returned === "function" ) &&
                                            returned.then;
    
                                        // Handle a returned thenable
                                        if ( isFunction( then ) ) {
    
                                            // Special processors (notify) just wait for resolution
                                            if ( special ) {
                                                then.call(
                                                    returned,
                                                    resolve( maxDepth, deferred, Identity, special ),
                                                    resolve( maxDepth, deferred, Thrower, special )
                                                );
    
                                            // Normal processors (resolve) also hook into progress
                                            } else {
    
                                                // ...and disregard older resolution values
                                                maxDepth++;
    
                                                then.call(
                                                    returned,
                                                    resolve( maxDepth, deferred, Identity, special ),
                                                    resolve( maxDepth, deferred, Thrower, special ),
                                                    resolve( maxDepth, deferred, Identity,
                                                        deferred.notifyWith )
                                                );
                                            }
    
                                        // Handle all other returned values
                                        } else {
    
                                            // Only substitute handlers pass on context
                                            // and multiple values (non-spec behavior)
                                            if ( handler !== Identity ) {
                                                that = undefined;
                                                args = [ returned ];
                                            }
    
                                            // Process the value(s)
                                            // Default process is resolve
                                            ( special || deferred.resolveWith )( that, args );
                                        }
                                    },
    
                                    // Only normal processors (resolve) catch and reject exceptions
                                    process = special ?
                                        mightThrow :
                                        function() {
                                            try {
                                                mightThrow();
                                            } catch ( e ) {
    
                                                if ( jQuery.Deferred.exceptionHook ) {
                                                    jQuery.Deferred.exceptionHook( e,
                                                        process.stackTrace );
                                                }
    
                                                // Support: Promises/A+ section 2.3.3.3.4.1
                                                // https://promisesaplus.com/#point-61
                                                // Ignore post-resolution exceptions
                                                if ( depth + 1 >= maxDepth ) {
    
                                                    // Only substitute handlers pass on context
                                                    // and multiple values (non-spec behavior)
                                                    if ( handler !== Thrower ) {
                                                        that = undefined;
                                                        args = [ e ];
                                                    }
    
                                                    deferred.rejectWith( that, args );
                                                }
                                            }
                                        };
    
                                // Support: Promises/A+ section 2.3.3.3.1
                                // https://promisesaplus.com/#point-57
                                // Re-resolve promises immediately to dodge false rejection from
                                // subsequent errors
                                if ( depth ) {
                                    process();
                                } else {
    
                                    // Call an optional hook to record the stack, in case of exception
                                    // since it's otherwise lost when execution goes async
                                    if ( jQuery.Deferred.getStackHook ) {
                                        process.stackTrace = jQuery.Deferred.getStackHook();
                                    }
                                    window.setTimeout( process );
                                }
                            };
                        }
    
                        return jQuery.Deferred( function( newDefer ) {
    
                            // progress_handlers.add( ... )
                            tuples[ 0 ][ 3 ].add(
                                resolve(
                                    0,
                                    newDefer,
                                    isFunction( onProgress ) ?
                                        onProgress :
                                        Identity,
                                    newDefer.notifyWith
                                )
                            );
    
                            // fulfilled_handlers.add( ... )
                            tuples[ 1 ][ 3 ].add(
                                resolve(
                                    0,
                                    newDefer,
                                    isFunction( onFulfilled ) ?
                                        onFulfilled :
                                        Identity
                                )
                            );
    
                            // rejected_handlers.add( ... )
                            tuples[ 2 ][ 3 ].add(
                                resolve(
                                    0,
                                    newDefer,
                                    isFunction( onRejected ) ?
                                        onRejected :
                                        Thrower
                                )
                            );
                        } ).promise();
                    },
    
                    // Get a promise for this deferred
                    // If obj is provided, the promise aspect is added to the object
                    promise: function( obj ) {
                        return obj != null ? jQuery.extend( obj, promise ) : promise;
                    }
                },
                deferred = {};
    
            // Add list-specific methods
            jQuery.each( tuples, function( i, tuple ) {
                var list = tuple[ 2 ],
                    stateString = tuple[ 5 ];
    
                // promise.progress = list.add
                // promise.done = list.add
                // promise.fail = list.add
                promise[ tuple[ 1 ] ] = list.add;
    
                // Handle state
                if ( stateString ) {
                    list.add(
                        function() {
    
                            // state = "resolved" (i.e., fulfilled)
                            // state = "rejected"
                            state = stateString;
                        },
    
                        // rejected_callbacks.disable
                        // fulfilled_callbacks.disable
                        tuples[ 3 - i ][ 2 ].disable,
    
                        // rejected_handlers.disable
                        // fulfilled_handlers.disable
                        tuples[ 3 - i ][ 3 ].disable,
    
                        // progress_callbacks.lock
                        tuples[ 0 ][ 2 ].lock,
    
                        // progress_handlers.lock
                        tuples[ 0 ][ 3 ].lock
                    );
                }
    
                // progress_handlers.fire
                // fulfilled_handlers.fire
                // rejected_handlers.fire
                list.add( tuple[ 3 ].fire );
    
                // deferred.notify = function() { deferred.notifyWith(...) }
                // deferred.resolve = function() { deferred.resolveWith(...) }
                // deferred.reject = function() { deferred.rejectWith(...) }
                deferred[ tuple[ 0 ] ] = function() {
                    deferred[ tuple[ 0 ] + "With" ]( this === deferred ? undefined : this, arguments );
                    return this;
                };
    
                // deferred.notifyWith = list.fireWith
                // deferred.resolveWith = list.fireWith
                // deferred.rejectWith = list.fireWith
                deferred[ tuple[ 0 ] + "With" ] = list.fireWith;
            } );
    
            // Make the deferred a promise
            promise.promise( deferred );
    
            // Call given func if any
            if ( func ) {
                func.call( deferred, deferred );
            }
    
            // All done!
            return deferred;
        },
    
        // Deferred helper
        when: function( singleValue ) {
            var
    
                // count of uncompleted subordinates
                remaining = arguments.length,
    
                // count of unprocessed arguments
                i = remaining,
    
                // subordinate fulfillment data
                resolveContexts = Array( i ),
                resolveValues = slice.call( arguments ),
    
                // the primary Deferred
                primary = jQuery.Deferred(),
    
                // subordinate callback factory
                updateFunc = function( i ) {
                    return function( value ) {
                        resolveContexts[ i ] = this;
                        resolveValues[ i ] = arguments.length > 1 ? slice.call( arguments ) : value;
                        if ( !( --remaining ) ) {
                            primary.resolveWith( resolveContexts, resolveValues );
                        }
                    };
                };
    
            // Single- and empty arguments are adopted like Promise.resolve
            if ( remaining <= 1 ) {
                adoptValue( singleValue, primary.done( updateFunc( i ) ).resolve, primary.reject,
                    !remaining );
    
                // Use .then() to unwrap secondary thenables (cf. gh-3000)
                if ( primary.state() === "pending" ||
                    isFunction( resolveValues[ i ] && resolveValues[ i ].then ) ) {
    
                    return primary.then();
                }
            }
    
            // Multiple arguments are aggregated like Promise.all array elements
            while ( i-- ) {
                adoptValue( resolveValues[ i ], updateFunc( i ), primary.reject );
            }
    
            return primary.promise();
        }
    } );
    
    
    // These usually indicate a programmer mistake during development,
    // warn about them ASAP rather than swallowing them by default.
    var rerrorNames = /^(Eval|Internal|Range|Reference|Syntax|Type|URI)Error$/;
    
    jQuery.Deferred.exceptionHook = function( error, stack ) {
    
        // Support: IE 8 - 9 only
        // Console exists when dev tools are open, which can happen at any time
        if ( window.console && window.console.warn && error && rerrorNames.test( error.name ) ) {
            window.console.warn( "jQuery.Deferred exception: " + error.message, error.stack, stack );
        }
    };
    
    
    
    
    jQuery.readyException = function( error ) {
        window.setTimeout( function() {
            throw error;
        } );
    };
    
    
    
    
    // The deferred used on DOM ready
    var readyList = jQuery.Deferred();
    
    jQuery.fn.ready = function( fn ) {
    
        readyList
            .then( fn )
    
            // Wrap jQuery.readyException in a function so that the lookup
            // happens at the time of error handling instead of callback
            // registration.
            .catch( function( error ) {
                jQuery.readyException( error );
            } );
    
        return this;
    };
    
    jQuery.extend( {
    
        // Is the DOM ready to be used? Set to true once it occurs.
        isReady: false,
    
        // A counter to track how many items to wait for before
        // the ready event fires. See #6781
        readyWait: 1,
    
        // Handle when the DOM is ready
        ready: function( wait ) {
    
            // Abort if there are pending holds or we're already ready
            if ( wait === true ? --jQuery.readyWait : jQuery.isReady ) {
                return;
            }
    
            // Remember that the DOM is ready
            jQuery.isReady = true;
    
            // If a normal DOM Ready event fired, decrement, and wait if need be
            if ( wait !== true && --jQuery.readyWait > 0 ) {
                return;
            }
    
            // If there are functions bound, to execute
            readyList.resolveWith( document, [ jQuery ] );
        }
    } );
    
    jQuery.ready.then = readyList.then;
    
    // The ready event handler and self cleanup method
    function completed() {
        document.removeEventListener( "DOMContentLoaded", completed );
        window.removeEventListener( "load", completed );
        jQuery.ready();
    }
    
    // Catch cases where $(document).ready() is called
    // after the browser event has already occurred.
    // Support: IE <=9 - 10 only
    // Older IE sometimes signals "interactive" too soon
    if ( document.readyState === "complete" ||
        ( document.readyState !== "loading" && !document.documentElement.doScroll ) ) {
    
        // Handle it asynchronously to allow scripts the opportunity to delay ready
        window.setTimeout( jQuery.ready );
    
    } else {
    
        // Use the handy event callback
        document.addEventListener( "DOMContentLoaded", completed );
    
        // A fallback to window.onload, that will always work
        window.addEventListener( "load", completed );
    }
    
    
    
    
    // Multifunctional method to get and set values of a collection
    // The value/s can optionally be executed if it's a function
    var access = function( elems, fn, key, value, chainable, emptyGet, raw ) {
        var i = 0,
            len = elems.length,
            bulk = key == null;
    
        // Sets many values
        if ( toType( key ) === "object" ) {
            chainable = true;
            for ( i in key ) {
                access( elems, fn, i, key[ i ], true, emptyGet, raw );
            }
    
        // Sets one value
        } else if ( value !== undefined ) {
            chainable = true;
    
            if ( !isFunction( value ) ) {
                raw = true;
            }
    
            if ( bulk ) {
    
                // Bulk operations run against the entire set
                if ( raw ) {
                    fn.call( elems, value );
                    fn = null;
    
                // ...except when executing function values
                } else {
                    bulk = fn;
                    fn = function( elem, _key, value ) {
                        return bulk.call( jQuery( elem ), value );
                    };
                }
            }
    
            if ( fn ) {
                for ( ; i < len; i++ ) {
                    fn(
                        elems[ i ], key, raw ?
                            value :
                            value.call( elems[ i ], i, fn( elems[ i ], key ) )
                    );
                }
            }
        }
    
        if ( chainable ) {
            return elems;
        }
    
        // Gets
        if ( bulk ) {
            return fn.call( elems );
        }
    
        return len ? fn( elems[ 0 ], key ) : emptyGet;
    };
    
    
    // Matches dashed string for camelizing
    var rmsPrefix = /^-ms-/,
        rdashAlpha = /-([a-z])/g;
    
    // Used by camelCase as callback to replace()
    function fcamelCase( _all, letter ) {
        return letter.toUpperCase();
    }
    
    // Convert dashed to camelCase; used by the css and data modules
    // Support: IE <=9 - 11, Edge 12 - 15
    // Microsoft forgot to hump their vendor prefix (#9572)
    function camelCase( string ) {
        return string.replace( rmsPrefix, "ms-" ).replace( rdashAlpha, fcamelCase );
    }
    var acceptData = function( owner ) {
    
        // Accepts only:
        //  - Node
        //    - Node.ELEMENT_NODE
        //    - Node.DOCUMENT_NODE
        //  - Object
        //    - Any
        return owner.nodeType === 1 || owner.nodeType === 9 || !( +owner.nodeType );
    };
    
    
    
    
    function Data() {
        this.expando = jQuery.expando + Data.uid++;
    }
    
    Data.uid = 1;
    
    Data.prototype = {
    
        cache: function( owner ) {
    
            // Check if the owner object already has a cache
            var value = owner[ this.expando ];
    
            // If not, create one
            if ( !value ) {
                value = {};
    
                // We can accept data for non-element nodes in modern browsers,
                // but we should not, see #8335.
                // Always return an empty object.
                if ( acceptData( owner ) ) {
    
                    // If it is a node unlikely to be stringify-ed or looped over
                    // use plain assignment
                    if ( owner.nodeType ) {
                        owner[ this.expando ] = value;
    
                    // Otherwise secure it in a non-enumerable property
                    // configurable must be true to allow the property to be
                    // deleted when data is removed
                    } else {
                        Object.defineProperty( owner, this.expando, {
                            value: value,
                            configurable: true
                        } );
                    }
                }
            }
    
            return value;
        },
        set: function( owner, data, value ) {
            var prop,
                cache = this.cache( owner );
    
            // Handle: [ owner, key, value ] args
            // Always use camelCase key (gh-2257)
            if ( typeof data === "string" ) {
                cache[ camelCase( data ) ] = value;
    
            // Handle: [ owner, { properties } ] args
            } else {
    
                // Copy the properties one-by-one to the cache object
                for ( prop in data ) {
                    cache[ camelCase( prop ) ] = data[ prop ];
                }
            }
            return cache;
        },
        get: function( owner, key ) {
            return key === undefined ?
                this.cache( owner ) :
    
                // Always use camelCase key (gh-2257)
                owner[ this.expando ] && owner[ this.expando ][ camelCase( key ) ];
        },
        access: function( owner, key, value ) {
    
            // In cases where either:
            //
            //   1. No key was specified
            //   2. A string key was specified, but no value provided
            //
            // Take the "read" path and allow the get method to determine
            // which value to return, respectively either:
            //
            //   1. The entire cache object
            //   2. The data stored at the key
            //
            if ( key === undefined ||
                    ( ( key && typeof key === "string" ) && value === undefined ) ) {
    
                return this.get( owner, key );
            }
    
            // When the key is not a string, or both a key and value
            // are specified, set or extend (existing objects) with either:
            //
            //   1. An object of properties
            //   2. A key and value
            //
            this.set( owner, key, value );
    
            // Since the "set" path can have two possible entry points
            // return the expected data based on which path was taken[*]
            return value !== undefined ? value : key;
        },
        remove: function( owner, key ) {
            var i,
                cache = owner[ this.expando ];
    
            if ( cache === undefined ) {
                return;
            }
    
            if ( key !== undefined ) {
    
                // Support array or space separated string of keys
                if ( Array.isArray( key ) ) {
    
                    // If key is an array of keys...
                    // We always set camelCase keys, so remove that.
                    key = key.map( camelCase );
                } else {
                    key = camelCase( key );
    
                    // If a key with the spaces exists, use it.
                    // Otherwise, create an array by matching non-whitespace
                    key = key in cache ?
                        [ key ] :
                        ( key.match( rnothtmlwhite ) || [] );
                }
    
                i = key.length;
    
                while ( i-- ) {
                    delete cache[ key[ i ] ];
                }
            }
    
            // Remove the expando if there's no more data
            if ( key === undefined || jQuery.isEmptyObject( cache ) ) {
    
                // Support: Chrome <=35 - 45
                // Webkit & Blink performance suffers when deleting properties
                // from DOM nodes, so set to undefined instead
                // https://bugs.chromium.org/p/chromium/issues/detail?id=378607 (bug restricted)
                if ( owner.nodeType ) {
                    owner[ this.expando ] = undefined;
                } else {
                    delete owner[ this.expando ];
                }
            }
        },
        hasData: function( owner ) {
            var cache = owner[ this.expando ];
            return cache !== undefined && !jQuery.isEmptyObject( cache );
        }
    };
    var dataPriv = new Data();
    
    var dataUser = new Data();
    
    
    
    //	Implementation Summary
    //
    //	1. Enforce API surface and semantic compatibility with 1.9.x branch
    //	2. Improve the module's maintainability by reducing the storage
    //		paths to a single mechanism.
    //	3. Use the same single mechanism to support "private" and "user" data.
    //	4. _Never_ expose "private" data to user code (TODO: Drop _data, _removeData)
    //	5. Avoid exposing implementation details on user objects (eg. expando properties)
    //	6. Provide a clear path for implementation upgrade to WeakMap in 2014
    
    var rbrace = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/,
        rmultiDash = /[A-Z]/g;
    
    function getData( data ) {
        if ( data === "true" ) {
            return true;
        }
    
        if ( data === "false" ) {
            return false;
        }
    
        if ( data === "null" ) {
            return null;
        }
    
        // Only convert to a number if it doesn't change the string
        if ( data === +data + "" ) {
            return +data;
        }
    
        if ( rbrace.test( data ) ) {
            return JSON.parse( data );
        }
    
        return data;
    }
    
    function dataAttr( elem, key, data ) {
        var name;
    
        // If nothing was found internally, try to fetch any
        // data from the HTML5 data-* attribute
        if ( data === undefined && elem.nodeType === 1 ) {
            name = "data-" + key.replace( rmultiDash, "-$&" ).toLowerCase();
            data = elem.getAttribute( name );
    
            if ( typeof data === "string" ) {
                try {
                    data = getData( data );
                } catch ( e ) {}
    
                // Make sure we set the data so it isn't changed later
                dataUser.set( elem, key, data );
            } else {
                data = undefined;
            }
        }
        return data;
    }
    
    jQuery.extend( {
        hasData: function( elem ) {
            return dataUser.hasData( elem ) || dataPriv.hasData( elem );
        },
    
        data: function( elem, name, data ) {
            return dataUser.access( elem, name, data );
        },
    
        removeData: function( elem, name ) {
            dataUser.remove( elem, name );
        },
    
        // TODO: Now that all calls to _data and _removeData have been replaced
        // with direct calls to dataPriv methods, these can be deprecated.
        _data: function( elem, name, data ) {
            return dataPriv.access( elem, name, data );
        },
    
        _removeData: function( elem, name ) {
            dataPriv.remove( elem, name );
        }
    } );
    
    jQuery.fn.extend( {
        data: function( key, value ) {
            var i, name, data,
                elem = this[ 0 ],
                attrs = elem && elem.attributes;
    
            // Gets all values
            if ( key === undefined ) {
                if ( this.length ) {
                    data = dataUser.get( elem );
    
                    if ( elem.nodeType === 1 && !dataPriv.get( elem, "hasDataAttrs" ) ) {
                        i = attrs.length;
                        while ( i-- ) {
    
                            // Support: IE 11 only
                            // The attrs elements can be null (#14894)
                            if ( attrs[ i ] ) {
                                name = attrs[ i ].name;
                                if ( name.indexOf( "data-" ) === 0 ) {
                                    name = camelCase( name.slice( 5 ) );
                                    dataAttr( elem, name, data[ name ] );
                                }
                            }
                        }
                        dataPriv.set( elem, "hasDataAttrs", true );
                    }
                }
    
                return data;
            }
    
            // Sets multiple values
            if ( typeof key === "object" ) {
                return this.each( function() {
                    dataUser.set( this, key );
                } );
            }
    
            return access( this, function( value ) {
                var data;
    
                // The calling jQuery object (element matches) is not empty
                // (and therefore has an element appears at this[ 0 ]) and the
                // `value` parameter was not undefined. An empty jQuery object
                // will result in `undefined` for elem = this[ 0 ] which will
                // throw an exception if an attempt to read a data cache is made.
                if ( elem && value === undefined ) {
    
                    // Attempt to get data from the cache
                    // The key will always be camelCased in Data
                    data = dataUser.get( elem, key );
                    if ( data !== undefined ) {
                        return data;
                    }
    
                    // Attempt to "discover" the data in
                    // HTML5 custom data-* attrs
                    data = dataAttr( elem, key );
                    if ( data !== undefined ) {
                        return data;
                    }
    
                    // We tried really hard, but the data doesn't exist.
                    return;
                }
    
                // Set the data...
                this.each( function() {
    
                    // We always store the camelCased key
                    dataUser.set( this, key, value );
                } );
            }, null, value, arguments.length > 1, null, true );
        },
    
        removeData: function( key ) {
            return this.each( function() {
                dataUser.remove( this, key );
            } );
        }
    } );
    
    
    jQuery.extend( {
        queue: function( elem, type, data ) {
            var queue;
    
            if ( elem ) {
                type = ( type || "fx" ) + "queue";
                queue = dataPriv.get( elem, type );
    
                // Speed up dequeue by getting out quickly if this is just a lookup
                if ( data ) {
                    if ( !queue || Array.isArray( data ) ) {
                        queue = dataPriv.access( elem, type, jQuery.makeArray( data ) );
                    } else {
                        queue.push( data );
                    }
                }
                return queue || [];
            }
        },
    
        dequeue: function( elem, type ) {
            type = type || "fx";
    
            var queue = jQuery.queue( elem, type ),
                startLength = queue.length,
                fn = queue.shift(),
                hooks = jQuery._queueHooks( elem, type ),
                next = function() {
                    jQuery.dequeue( elem, type );
                };
    
            // If the fx queue is dequeued, always remove the progress sentinel
            if ( fn === "inprogress" ) {
                fn = queue.shift();
                startLength--;
            }
    
            if ( fn ) {
    
                // Add a progress sentinel to prevent the fx queue from being
                // automatically dequeued
                if ( type === "fx" ) {
                    queue.unshift( "inprogress" );
                }
    
                // Clear up the last queue stop function
                delete hooks.stop;
                fn.call( elem, next, hooks );
            }
    
            if ( !startLength && hooks ) {
                hooks.empty.fire();
            }
        },
    
        // Not public - generate a queueHooks object, or return the current one
        _queueHooks: function( elem, type ) {
            var key = type + "queueHooks";
            return dataPriv.get( elem, key ) || dataPriv.access( elem, key, {
                empty: jQuery.Callbacks( "once memory" ).add( function() {
                    dataPriv.remove( elem, [ type + "queue", key ] );
                } )
            } );
        }
    } );
    
    jQuery.fn.extend( {
        queue: function( type, data ) {
            var setter = 2;
    
            if ( typeof type !== "string" ) {
                data = type;
                type = "fx";
                setter--;
            }
    
            if ( arguments.length < setter ) {
                return jQuery.queue( this[ 0 ], type );
            }
    
            return data === undefined ?
                this :
                this.each( function() {
                    var queue = jQuery.queue( this, type, data );
    
                    // Ensure a hooks for this queue
                    jQuery._queueHooks( this, type );
    
                    if ( type === "fx" && queue[ 0 ] !== "inprogress" ) {
                        jQuery.dequeue( this, type );
                    }
                } );
        },
        dequeue: function( type ) {
            return this.each( function() {
                jQuery.dequeue( this, type );
            } );
        },
        clearQueue: function( type ) {
            return this.queue( type || "fx", [] );
        },
    
        // Get a promise resolved when queues of a certain type
        // are emptied (fx is the type by default)
        promise: function( type, obj ) {
            var tmp,
                count = 1,
                defer = jQuery.Deferred(),
                elements = this,
                i = this.length,
                resolve = function() {
                    if ( !( --count ) ) {
                        defer.resolveWith( elements, [ elements ] );
                    }
                };
    
            if ( typeof type !== "string" ) {
                obj = type;
                type = undefined;
            }
            type = type || "fx";
    
            while ( i-- ) {
                tmp = dataPriv.get( elements[ i ], type + "queueHooks" );
                if ( tmp && tmp.empty ) {
                    count++;
                    tmp.empty.add( resolve );
                }
            }
            resolve();
            return defer.promise( obj );
        }
    } );
    var pnum = ( /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/ ).source;
    
    var rcssNum = new RegExp( "^(?:([+-])=|)(" + pnum + ")([a-z%]*)$", "i" );
    
    
    var cssExpand = [ "Top", "Right", "Bottom", "Left" ];
    
    var documentElement = document.documentElement;
    
    
    
        var isAttached = function( elem ) {
                return jQuery.contains( elem.ownerDocument, elem );
            },
            composed = { composed: true };
    
        // Support: IE 9 - 11+, Edge 12 - 18+, iOS 10.0 - 10.2 only
        // Check attachment across shadow DOM boundaries when possible (gh-3504)
        // Support: iOS 10.0-10.2 only
        // Early iOS 10 versions support `attachShadow` but not `getRootNode`,
        // leading to errors. We need to check for `getRootNode`.
        if ( documentElement.getRootNode ) {
            isAttached = function( elem ) {
                return jQuery.contains( elem.ownerDocument, elem ) ||
                    elem.getRootNode( composed ) === elem.ownerDocument;
            };
        }
    var isHiddenWithinTree = function( elem, el ) {
    
            // isHiddenWithinTree might be called from jQuery#filter function;
            // in that case, element will be second argument
            elem = el || elem;
    
            // Inline style trumps all
            return elem.style.display === "none" ||
                elem.style.display === "" &&
    
                // Otherwise, check computed style
                // Support: Firefox <=43 - 45
                // Disconnected elements can have computed display: none, so first confirm that elem is
                // in the document.
                isAttached( elem ) &&
    
                jQuery.css( elem, "display" ) === "none";
        };
    
    
    
    function adjustCSS( elem, prop, valueParts, tween ) {
        var adjusted, scale,
            maxIterations = 20,
            currentValue = tween ?
                function() {
                    return tween.cur();
                } :
                function() {
                    return jQuery.css( elem, prop, "" );
                },
            initial = currentValue(),
            unit = valueParts && valueParts[ 3 ] || ( jQuery.cssNumber[ prop ] ? "" : "px" ),
    
            // Starting value computation is required for potential unit mismatches
            initialInUnit = elem.nodeType &&
                ( jQuery.cssNumber[ prop ] || unit !== "px" && +initial ) &&
                rcssNum.exec( jQuery.css( elem, prop ) );
    
        if ( initialInUnit && initialInUnit[ 3 ] !== unit ) {
    
            // Support: Firefox <=54
            // Halve the iteration target value to prevent interference from CSS upper bounds (gh-2144)
            initial = initial / 2;
    
            // Trust units reported by jQuery.css
            unit = unit || initialInUnit[ 3 ];
    
            // Iteratively approximate from a nonzero starting point
            initialInUnit = +initial || 1;
    
            while ( maxIterations-- ) {
    
                // Evaluate and update our best guess (doubling guesses that zero out).
                // Finish if the scale equals or crosses 1 (making the old*new product non-positive).
                jQuery.style( elem, prop, initialInUnit + unit );
                if ( ( 1 - scale ) * ( 1 - ( scale = currentValue() / initial || 0.5 ) ) <= 0 ) {
                    maxIterations = 0;
                }
                initialInUnit = initialInUnit / scale;
    
            }
    
            initialInUnit = initialInUnit * 2;
            jQuery.style( elem, prop, initialInUnit + unit );
    
            // Make sure we update the tween properties later on
            valueParts = valueParts || [];
        }
    
        if ( valueParts ) {
            initialInUnit = +initialInUnit || +initial || 0;
    
            // Apply relative offset (+=/-=) if specified
            adjusted = valueParts[ 1 ] ?
                initialInUnit + ( valueParts[ 1 ] + 1 ) * valueParts[ 2 ] :
                +valueParts[ 2 ];
            if ( tween ) {
                tween.unit = unit;
                tween.start = initialInUnit;
                tween.end = adjusted;
            }
        }
        return adjusted;
    }
    
    
    var defaultDisplayMap = {};
    
    function getDefaultDisplay( elem ) {
        var temp,
            doc = elem.ownerDocument,
            nodeName = elem.nodeName,
            display = defaultDisplayMap[ nodeName ];
    
        if ( display ) {
            return display;
        }
    
        temp = doc.body.appendChild( doc.createElement( nodeName ) );
        display = jQuery.css( temp, "display" );
    
        temp.parentNode.removeChild( temp );
    
        if ( display === "none" ) {
            display = "block";
        }
        defaultDisplayMap[ nodeName ] = display;
    
        return display;
    }
    
    function showHide( elements, show ) {
        var display, elem,
            values = [],
            index = 0,
            length = elements.length;
    
        // Determine new display value for elements that need to change
        for ( ; index < length; index++ ) {
            elem = elements[ index ];
            if ( !elem.style ) {
                continue;
            }
    
            display = elem.style.display;
            if ( show ) {
    
                // Since we force visibility upon cascade-hidden elements, an immediate (and slow)
                // check is required in this first loop unless we have a nonempty display value (either
                // inline or about-to-be-restored)
                if ( display === "none" ) {
                    values[ index ] = dataPriv.get( elem, "display" ) || null;
                    if ( !values[ index ] ) {
                        elem.style.display = "";
                    }
                }
                if ( elem.style.display === "" && isHiddenWithinTree( elem ) ) {
                    values[ index ] = getDefaultDisplay( elem );
                }
            } else {
                if ( display !== "none" ) {
                    values[ index ] = "none";
    
                    // Remember what we're overwriting
                    dataPriv.set( elem, "display", display );
                }
            }
        }
    
        // Set the display of the elements in a second loop to avoid constant reflow
        for ( index = 0; index < length; index++ ) {
            if ( values[ index ] != null ) {
                elements[ index ].style.display = values[ index ];
            }
        }
    
        return elements;
    }
    
    jQuery.fn.extend( {
        show: function() {
            return showHide( this, true );
        },
        hide: function() {
            return showHide( this );
        },
        toggle: function( state ) {
            if ( typeof state === "boolean" ) {
                return state ? this.show() : this.hide();
            }
    
            return this.each( function() {
                if ( isHiddenWithinTree( this ) ) {
                    jQuery( this ).show();
                } else {
                    jQuery( this ).hide();
                }
            } );
        }
    } );
    var rcheckableType = ( /^(?:checkbox|radio)$/i );
    
    var rtagName = ( /<([a-z][^\/\0>\x20\t\r\n\f]*)/i );
    
    var rscriptType = ( /^$|^module$|\/(?:java|ecma)script/i );
    
    
    
    ( function() {
        var fragment = document.createDocumentFragment(),
            div = fragment.appendChild( document.createElement( "div" ) ),
            input = document.createElement( "input" );
    
        // Support: Android 4.0 - 4.3 only
        // Check state lost if the name is set (#11217)
        // Support: Windows Web Apps (WWA)
        // `name` and `type` must use .setAttribute for WWA (#14901)
        input.setAttribute( "type", "radio" );
        input.setAttribute( "checked", "checked" );
        input.setAttribute( "name", "t" );
    
        div.appendChild( input );
    
        // Support: Android <=4.1 only
        // Older WebKit doesn't clone checked state correctly in fragments
        support.checkClone = div.cloneNode( true ).cloneNode( true ).lastChild.checked;
    
        // Support: IE <=11 only
        // Make sure textarea (and checkbox) defaultValue is properly cloned
        div.innerHTML = "<textarea>x</textarea>";
        support.noCloneChecked = !!div.cloneNode( true ).lastChild.defaultValue;
    
        // Support: IE <=9 only
        // IE <=9 replaces <option> tags with their contents when inserted outside of
        // the select element.
        div.innerHTML = "<option></option>";
        support.option = !!div.lastChild;
    } )();
    
    
    // We have to close these tags to support XHTML (#13200)
    var wrapMap = {
    
        // XHTML parsers do not magically insert elements in the
        // same way that tag soup parsers do. So we cannot shorten
        // this by omitting <tbody> or other required elements.
        thead: [ 1, "<table>", "</table>" ],
        col: [ 2, "<table><colgroup>", "</colgroup></table>" ],
        tr: [ 2, "<table><tbody>", "</tbody></table>" ],
        td: [ 3, "<table><tbody><tr>", "</tr></tbody></table>" ],
    
        _default: [ 0, "", "" ]
    };
    
    wrapMap.tbody = wrapMap.tfoot = wrapMap.colgroup = wrapMap.caption = wrapMap.thead;
    wrapMap.th = wrapMap.td;
    
    // Support: IE <=9 only
    if ( !support.option ) {
        wrapMap.optgroup = wrapMap.option = [ 1, "<select multiple='multiple'>", "</select>" ];
    }
    
    
    function getAll( context, tag ) {
    
        // Support: IE <=9 - 11 only
        // Use typeof to avoid zero-argument method invocation on host objects (#15151)
        var ret;
    
        if ( typeof context.getElementsByTagName !== "undefined" ) {
            ret = context.getElementsByTagName( tag || "*" );
    
        } else if ( typeof context.querySelectorAll !== "undefined" ) {
            ret = context.querySelectorAll( tag || "*" );
    
        } else {
            ret = [];
        }
    
        if ( tag === undefined || tag && nodeName( context, tag ) ) {
            return jQuery.merge( [ context ], ret );
        }
    
        return ret;
    }
    
    
    // Mark scripts as having already been evaluated
    function setGlobalEval( elems, refElements ) {
        var i = 0,
            l = elems.length;
    
        for ( ; i < l; i++ ) {
            dataPriv.set(
                elems[ i ],
                "globalEval",
                !refElements || dataPriv.get( refElements[ i ], "globalEval" )
            );
        }
    }
    
    
    var rhtml = /<|&#?\w+;/;
    
    function buildFragment( elems, context, scripts, selection, ignored ) {
        var elem, tmp, tag, wrap, attached, j,
            fragment = context.createDocumentFragment(),
            nodes = [],
            i = 0,
            l = elems.length;
    
        for ( ; i < l; i++ ) {
            elem = elems[ i ];
    
            if ( elem || elem === 0 ) {
    
                // Add nodes directly
                if ( toType( elem ) === "object" ) {
    
                    // Support: Android <=4.0 only, PhantomJS 1 only
                    // push.apply(_, arraylike) throws on ancient WebKit
                    jQuery.merge( nodes, elem.nodeType ? [ elem ] : elem );
    
                // Convert non-html into a text node
                } else if ( !rhtml.test( elem ) ) {
                    nodes.push( context.createTextNode( elem ) );
    
                // Convert html into DOM nodes
                } else {
                    tmp = tmp || fragment.appendChild( context.createElement( "div" ) );
    
                    // Deserialize a standard representation
                    tag = ( rtagName.exec( elem ) || [ "", "" ] )[ 1 ].toLowerCase();
                    wrap = wrapMap[ tag ] || wrapMap._default;
                    tmp.innerHTML = wrap[ 1 ] + jQuery.htmlPrefilter( elem ) + wrap[ 2 ];
    
                    // Descend through wrappers to the right content
                    j = wrap[ 0 ];
                    while ( j-- ) {
                        tmp = tmp.lastChild;
                    }
    
                    // Support: Android <=4.0 only, PhantomJS 1 only
                    // push.apply(_, arraylike) throws on ancient WebKit
                    jQuery.merge( nodes, tmp.childNodes );
    
                    // Remember the top-level container
                    tmp = fragment.firstChild;
    
                    // Ensure the created nodes are orphaned (#12392)
                    tmp.textContent = "";
                }
            }
        }
    
        // Remove wrapper from fragment
        fragment.textContent = "";
    
        i = 0;
        while ( ( elem = nodes[ i++ ] ) ) {
    
            // Skip elements already in the context collection (trac-4087)
            if ( selection && jQuery.inArray( elem, selection ) > -1 ) {
                if ( ignored ) {
                    ignored.push( elem );
                }
                continue;
            }
    
            attached = isAttached( elem );
    
            // Append to fragment
            tmp = getAll( fragment.appendChild( elem ), "script" );
    
            // Preserve script evaluation history
            if ( attached ) {
                setGlobalEval( tmp );
            }
    
            // Capture executables
            if ( scripts ) {
                j = 0;
                while ( ( elem = tmp[ j++ ] ) ) {
                    if ( rscriptType.test( elem.type || "" ) ) {
                        scripts.push( elem );
                    }
                }
            }
        }
    
        return fragment;
    }
    
    
    var rtypenamespace = /^([^.]*)(?:\.(.+)|)/;
    
    function returnTrue() {
        return true;
    }
    
    function returnFalse() {
        return false;
    }
    
    // Support: IE <=9 - 11+
    // focus() and blur() are asynchronous, except when they are no-op.
    // So expect focus to be synchronous when the element is already active,
    // and blur to be synchronous when the element is not already active.
    // (focus and blur are always synchronous in other supported browsers,
    // this just defines when we can count on it).
    function expectSync( elem, type ) {
        return ( elem === safeActiveElement() ) === ( type === "focus" );
    }
    
    // Support: IE <=9 only
    // Accessing document.activeElement can throw unexpectedly
    // https://bugs.jquery.com/ticket/13393
    function safeActiveElement() {
        try {
            return document.activeElement;
        } catch ( err ) { }
    }
    
    function on( elem, types, selector, data, fn, one ) {
        var origFn, type;
    
        // Types can be a map of types/handlers
        if ( typeof types === "object" ) {
    
            // ( types-Object, selector, data )
            if ( typeof selector !== "string" ) {
    
                // ( types-Object, data )
                data = data || selector;
                selector = undefined;
            }
            for ( type in types ) {
                on( elem, type, selector, data, types[ type ], one );
            }
            return elem;
        }
    
        if ( data == null && fn == null ) {
    
            // ( types, fn )
            fn = selector;
            data = selector = undefined;
        } else if ( fn == null ) {
            if ( typeof selector === "string" ) {
    
                // ( types, selector, fn )
                fn = data;
                data = undefined;
            } else {
    
                // ( types, data, fn )
                fn = data;
                data = selector;
                selector = undefined;
            }
        }
        if ( fn === false ) {
            fn = returnFalse;
        } else if ( !fn ) {
            return elem;
        }
    
        if ( one === 1 ) {
            origFn = fn;
            fn = function( event ) {
    
                // Can use an empty set, since event contains the info
                jQuery().off( event );
                return origFn.apply( this, arguments );
            };
    
            // Use same guid so caller can remove using origFn
            fn.guid = origFn.guid || ( origFn.guid = jQuery.guid++ );
        }
        return elem.each( function() {
            jQuery.event.add( this, types, fn, data, selector );
        } );
    }
    
    /*
     * Helper functions for managing events -- not part of the public interface.
     * Props to Dean Edwards' addEvent library for many of the ideas.
     */
    jQuery.event = {
    
        global: {},
    
        add: function( elem, types, handler, data, selector ) {
    
            var handleObjIn, eventHandle, tmp,
                events, t, handleObj,
                special, handlers, type, namespaces, origType,
                elemData = dataPriv.get( elem );
    
            // Only attach events to objects that accept data
            if ( !acceptData( elem ) ) {
                return;
            }
    
            // Caller can pass in an object of custom data in lieu of the handler
            if ( handler.handler ) {
                handleObjIn = handler;
                handler = handleObjIn.handler;
                selector = handleObjIn.selector;
            }
    
            // Ensure that invalid selectors throw exceptions at attach time
            // Evaluate against documentElement in case elem is a non-element node (e.g., document)
            if ( selector ) {
                jQuery.find.matchesSelector( documentElement, selector );
            }
    
            // Make sure that the handler has a unique ID, used to find/remove it later
            if ( !handler.guid ) {
                handler.guid = jQuery.guid++;
            }
    
            // Init the element's event structure and main handler, if this is the first
            if ( !( events = elemData.events ) ) {
                events = elemData.events = Object.create( null );
            }
            if ( !( eventHandle = elemData.handle ) ) {
                eventHandle = elemData.handle = function( e ) {
    
                    // Discard the second event of a jQuery.event.trigger() and
                    // when an event is called after a page has unloaded
                    return typeof jQuery !== "undefined" && jQuery.event.triggered !== e.type ?
                        jQuery.event.dispatch.apply( elem, arguments ) : undefined;
                };
            }
    
            // Handle multiple events separated by a space
            types = ( types || "" ).match( rnothtmlwhite ) || [ "" ];
            t = types.length;
            while ( t-- ) {
                tmp = rtypenamespace.exec( types[ t ] ) || [];
                type = origType = tmp[ 1 ];
                namespaces = ( tmp[ 2 ] || "" ).split( "." ).sort();
    
                // There *must* be a type, no attaching namespace-only handlers
                if ( !type ) {
                    continue;
                }
    
                // If event changes its type, use the special event handlers for the changed type
                special = jQuery.event.special[ type ] || {};
    
                // If selector defined, determine special event api type, otherwise given type
                type = ( selector ? special.delegateType : special.bindType ) || type;
    
                // Update special based on newly reset type
                special = jQuery.event.special[ type ] || {};
    
                // handleObj is passed to all event handlers
                handleObj = jQuery.extend( {
                    type: type,
                    origType: origType,
                    data: data,
                    handler: handler,
                    guid: handler.guid,
                    selector: selector,
                    needsContext: selector && jQuery.expr.match.needsContext.test( selector ),
                    namespace: namespaces.join( "." )
                }, handleObjIn );
    
                // Init the event handler queue if we're the first
                if ( !( handlers = events[ type ] ) ) {
                    handlers = events[ type ] = [];
                    handlers.delegateCount = 0;
    
                    // Only use addEventListener if the special events handler returns false
                    if ( !special.setup ||
                        special.setup.call( elem, data, namespaces, eventHandle ) === false ) {
    
                        if ( elem.addEventListener ) {
                            elem.addEventListener( type, eventHandle );
                        }
                    }
                }
    
                if ( special.add ) {
                    special.add.call( elem, handleObj );
    
                    if ( !handleObj.handler.guid ) {
                        handleObj.handler.guid = handler.guid;
                    }
                }
    
                // Add to the element's handler list, delegates in front
                if ( selector ) {
                    handlers.splice( handlers.delegateCount++, 0, handleObj );
                } else {
                    handlers.push( handleObj );
                }
    
                // Keep track of which events have ever been used, for event optimization
                jQuery.event.global[ type ] = true;
            }
    
        },
    
        // Detach an event or set of events from an element
        remove: function( elem, types, handler, selector, mappedTypes ) {
    
            var j, origCount, tmp,
                events, t, handleObj,
                special, handlers, type, namespaces, origType,
                elemData = dataPriv.hasData( elem ) && dataPriv.get( elem );
    
            if ( !elemData || !( events = elemData.events ) ) {
                return;
            }
    
            // Once for each type.namespace in types; type may be omitted
            types = ( types || "" ).match( rnothtmlwhite ) || [ "" ];
            t = types.length;
            while ( t-- ) {
                tmp = rtypenamespace.exec( types[ t ] ) || [];
                type = origType = tmp[ 1 ];
                namespaces = ( tmp[ 2 ] || "" ).split( "." ).sort();
    
                // Unbind all events (on this namespace, if provided) for the element
                if ( !type ) {
                    for ( type in events ) {
                        jQuery.event.remove( elem, type + types[ t ], handler, selector, true );
                    }
                    continue;
                }
    
                special = jQuery.event.special[ type ] || {};
                type = ( selector ? special.delegateType : special.bindType ) || type;
                handlers = events[ type ] || [];
                tmp = tmp[ 2 ] &&
                    new RegExp( "(^|\\.)" + namespaces.join( "\\.(?:.*\\.|)" ) + "(\\.|$)" );
    
                // Remove matching events
                origCount = j = handlers.length;
                while ( j-- ) {
                    handleObj = handlers[ j ];
    
                    if ( ( mappedTypes || origType === handleObj.origType ) &&
                        ( !handler || handler.guid === handleObj.guid ) &&
                        ( !tmp || tmp.test( handleObj.namespace ) ) &&
                        ( !selector || selector === handleObj.selector ||
                            selector === "**" && handleObj.selector ) ) {
                        handlers.splice( j, 1 );
    
                        if ( handleObj.selector ) {
                            handlers.delegateCount--;
                        }
                        if ( special.remove ) {
                            special.remove.call( elem, handleObj );
                        }
                    }
                }
    
                // Remove generic event handler if we removed something and no more handlers exist
                // (avoids potential for endless recursion during removal of special event handlers)
                if ( origCount && !handlers.length ) {
                    if ( !special.teardown ||
                        special.teardown.call( elem, namespaces, elemData.handle ) === false ) {
    
                        jQuery.removeEvent( elem, type, elemData.handle );
                    }
    
                    delete events[ type ];
                }
            }
    
            // Remove data and the expando if it's no longer used
            if ( jQuery.isEmptyObject( events ) ) {
                dataPriv.remove( elem, "handle events" );
            }
        },
    
        dispatch: function( nativeEvent ) {
    
            var i, j, ret, matched, handleObj, handlerQueue,
                args = new Array( arguments.length ),
    
                // Make a writable jQuery.Event from the native event object
                event = jQuery.event.fix( nativeEvent ),
    
                handlers = (
                    dataPriv.get( this, "events" ) || Object.create( null )
                )[ event.type ] || [],
                special = jQuery.event.special[ event.type ] || {};
    
            // Use the fix-ed jQuery.Event rather than the (read-only) native event
            args[ 0 ] = event;
    
            for ( i = 1; i < arguments.length; i++ ) {
                args[ i ] = arguments[ i ];
            }
    
            event.delegateTarget = this;
    
            // Call the preDispatch hook for the mapped type, and let it bail if desired
            if ( special.preDispatch && special.preDispatch.call( this, event ) === false ) {
                return;
            }
    
            // Determine handlers
            handlerQueue = jQuery.event.handlers.call( this, event, handlers );
    
            // Run delegates first; they may want to stop propagation beneath us
            i = 0;
            while ( ( matched = handlerQueue[ i++ ] ) && !event.isPropagationStopped() ) {
                event.currentTarget = matched.elem;
    
                j = 0;
                while ( ( handleObj = matched.handlers[ j++ ] ) &&
                    !event.isImmediatePropagationStopped() ) {
    
                    // If the event is namespaced, then each handler is only invoked if it is
                    // specially universal or its namespaces are a superset of the event's.
                    if ( !event.rnamespace || handleObj.namespace === false ||
                        event.rnamespace.test( handleObj.namespace ) ) {
    
                        event.handleObj = handleObj;
                        event.data = handleObj.data;
    
                        ret = ( ( jQuery.event.special[ handleObj.origType ] || {} ).handle ||
                            handleObj.handler ).apply( matched.elem, args );
    
                        if ( ret !== undefined ) {
                            if ( ( event.result = ret ) === false ) {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                        }
                    }
                }
            }
    
            // Call the postDispatch hook for the mapped type
            if ( special.postDispatch ) {
                special.postDispatch.call( this, event );
            }
    
            return event.result;
        },
    
        handlers: function( event, handlers ) {
            var i, handleObj, sel, matchedHandlers, matchedSelectors,
                handlerQueue = [],
                delegateCount = handlers.delegateCount,
                cur = event.target;
    
            // Find delegate handlers
            if ( delegateCount &&
    
                // Support: IE <=9
                // Black-hole SVG <use> instance trees (trac-13180)
                cur.nodeType &&
    
                // Support: Firefox <=42
                // Suppress spec-violating clicks indicating a non-primary pointer button (trac-3861)
                // https://www.w3.org/TR/DOM-Level-3-Events/#event-type-click
                // Support: IE 11 only
                // ...but not arrow key "clicks" of radio inputs, which can have `button` -1 (gh-2343)
                !( event.type === "click" && event.button >= 1 ) ) {
    
                for ( ; cur !== this; cur = cur.parentNode || this ) {
    
                    // Don't check non-elements (#13208)
                    // Don't process clicks on disabled elements (#6911, #8165, #11382, #11764)
                    if ( cur.nodeType === 1 && !( event.type === "click" && cur.disabled === true ) ) {
                        matchedHandlers = [];
                        matchedSelectors = {};
                        for ( i = 0; i < delegateCount; i++ ) {
                            handleObj = handlers[ i ];
    
                            // Don't conflict with Object.prototype properties (#13203)
                            sel = handleObj.selector + " ";
    
                            if ( matchedSelectors[ sel ] === undefined ) {
                                matchedSelectors[ sel ] = handleObj.needsContext ?
                                    jQuery( sel, this ).index( cur ) > -1 :
                                    jQuery.find( sel, this, null, [ cur ] ).length;
                            }
                            if ( matchedSelectors[ sel ] ) {
                                matchedHandlers.push( handleObj );
                            }
                        }
                        if ( matchedHandlers.length ) {
                            handlerQueue.push( { elem: cur, handlers: matchedHandlers } );
                        }
                    }
                }
            }
    
            // Add the remaining (directly-bound) handlers
            cur = this;
            if ( delegateCount < handlers.length ) {
                handlerQueue.push( { elem: cur, handlers: handlers.slice( delegateCount ) } );
            }
    
            return handlerQueue;
        },
    
        addProp: function( name, hook ) {
            Object.defineProperty( jQuery.Event.prototype, name, {
                enumerable: true,
                configurable: true,
    
                get: isFunction( hook ) ?
                    function() {
                        if ( this.originalEvent ) {
                            return hook( this.originalEvent );
                        }
                    } :
                    function() {
                        if ( this.originalEvent ) {
                            return this.originalEvent[ name ];
                        }
                    },
    
                set: function( value ) {
                    Object.defineProperty( this, name, {
                        enumerable: true,
                        configurable: true,
                        writable: true,
                        value: value
                    } );
                }
            } );
        },
    
        fix: function( originalEvent ) {
            return originalEvent[ jQuery.expando ] ?
                originalEvent :
                new jQuery.Event( originalEvent );
        },
    
        special: {
            load: {
    
                // Prevent triggered image.load events from bubbling to window.load
                noBubble: true
            },
            click: {
    
                // Utilize native event to ensure correct state for checkable inputs
                setup: function( data ) {
    
                    // For mutual compressibility with _default, replace `this` access with a local var.
                    // `|| data` is dead code meant only to preserve the variable through minification.
                    var el = this || data;
    
                    // Claim the first handler
                    if ( rcheckableType.test( el.type ) &&
                        el.click && nodeName( el, "input" ) ) {
    
                        // dataPriv.set( el, "click", ... )
                        leverageNative( el, "click", returnTrue );
                    }
    
                    // Return false to allow normal processing in the caller
                    return false;
                },
                trigger: function( data ) {
    
                    // For mutual compressibility with _default, replace `this` access with a local var.
                    // `|| data` is dead code meant only to preserve the variable through minification.
                    var el = this || data;
    
                    // Force setup before triggering a click
                    if ( rcheckableType.test( el.type ) &&
                        el.click && nodeName( el, "input" ) ) {
    
                        leverageNative( el, "click" );
                    }
    
                    // Return non-false to allow normal event-path propagation
                    return true;
                },
    
                // For cross-browser consistency, suppress native .click() on links
                // Also prevent it if we're currently inside a leveraged native-event stack
                _default: function( event ) {
                    var target = event.target;
                    return rcheckableType.test( target.type ) &&
                        target.click && nodeName( target, "input" ) &&
                        dataPriv.get( target, "click" ) ||
                        nodeName( target, "a" );
                }
            },
    
            beforeunload: {
                postDispatch: function( event ) {
    
                    // Support: Firefox 20+
                    // Firefox doesn't alert if the returnValue field is not set.
                    if ( event.result !== undefined && event.originalEvent ) {
                        event.originalEvent.returnValue = event.result;
                    }
                }
            }
        }
    };
    
    // Ensure the presence of an event listener that handles manually-triggered
    // synthetic events by interrupting progress until reinvoked in response to
    // *native* events that it fires directly, ensuring that state changes have
    // already occurred before other listeners are invoked.
    function leverageNative( el, type, expectSync ) {
    
        // Missing expectSync indicates a trigger call, which must force setup through jQuery.event.add
        if ( !expectSync ) {
            if ( dataPriv.get( el, type ) === undefined ) {
                jQuery.event.add( el, type, returnTrue );
            }
            return;
        }
    
        // Register the controller as a special universal handler for all event namespaces
        dataPriv.set( el, type, false );
        jQuery.event.add( el, type, {
            namespace: false,
            handler: function( event ) {
                var notAsync, result,
                    saved = dataPriv.get( this, type );
    
                if ( ( event.isTrigger & 1 ) && this[ type ] ) {
    
                    // Interrupt processing of the outer synthetic .trigger()ed event
                    // Saved data should be false in such cases, but might be a leftover capture object
                    // from an async native handler (gh-4350)
                    if ( !saved.length ) {
    
                        // Store arguments for use when handling the inner native event
                        // There will always be at least one argument (an event object), so this array
                        // will not be confused with a leftover capture object.
                        saved = slice.call( arguments );
                        dataPriv.set( this, type, saved );
    
                        // Trigger the native event and capture its result
                        // Support: IE <=9 - 11+
                        // focus() and blur() are asynchronous
                        notAsync = expectSync( this, type );
                        this[ type ]();
                        result = dataPriv.get( this, type );
                        if ( saved !== result || notAsync ) {
                            dataPriv.set( this, type, false );
                        } else {
                            result = {};
                        }
                        if ( saved !== result ) {
    
                            // Cancel the outer synthetic event
                            event.stopImmediatePropagation();
                            event.preventDefault();
    
                            // Support: Chrome 86+
                            // In Chrome, if an element having a focusout handler is blurred by
                            // clicking outside of it, it invokes the handler synchronously. If
                            // that handler calls `.remove()` on the element, the data is cleared,
                            // leaving `result` undefined. We need to guard against this.
                            return result && result.value;
                        }
    
                    // If this is an inner synthetic event for an event with a bubbling surrogate
                    // (focus or blur), assume that the surrogate already propagated from triggering the
                    // native event and prevent that from happening again here.
                    // This technically gets the ordering wrong w.r.t. to `.trigger()` (in which the
                    // bubbling surrogate propagates *after* the non-bubbling base), but that seems
                    // less bad than duplication.
                    } else if ( ( jQuery.event.special[ type ] || {} ).delegateType ) {
                        event.stopPropagation();
                    }
    
                // If this is a native event triggered above, everything is now in order
                // Fire an inner synthetic event with the original arguments
                } else if ( saved.length ) {
    
                    // ...and capture the result
                    dataPriv.set( this, type, {
                        value: jQuery.event.trigger(
    
                            // Support: IE <=9 - 11+
                            // Extend with the prototype to reset the above stopImmediatePropagation()
                            jQuery.extend( saved[ 0 ], jQuery.Event.prototype ),
                            saved.slice( 1 ),
                            this
                        )
                    } );
    
                    // Abort handling of the native event
                    event.stopImmediatePropagation();
                }
            }
        } );
    }
    
    jQuery.removeEvent = function( elem, type, handle ) {
    
        // This "if" is needed for plain objects
        if ( elem.removeEventListener ) {
            elem.removeEventListener( type, handle );
        }
    };
    
    jQuery.Event = function( src, props ) {
    
        // Allow instantiation without the 'new' keyword
        if ( !( this instanceof jQuery.Event ) ) {
            return new jQuery.Event( src, props );
        }
    
        // Event object
        if ( src && src.type ) {
            this.originalEvent = src;
            this.type = src.type;
    
            // Events bubbling up the document may have been marked as prevented
            // by a handler lower down the tree; reflect the correct value.
            this.isDefaultPrevented = src.defaultPrevented ||
                    src.defaultPrevented === undefined &&
    
                    // Support: Android <=2.3 only
                    src.returnValue === false ?
                returnTrue :
                returnFalse;
    
            // Create target properties
            // Support: Safari <=6 - 7 only
            // Target should not be a text node (#504, #13143)
            this.target = ( src.target && src.target.nodeType === 3 ) ?
                src.target.parentNode :
                src.target;
    
            this.currentTarget = src.currentTarget;
            this.relatedTarget = src.relatedTarget;
    
        // Event type
        } else {
            this.type = src;
        }
    
        // Put explicitly provided properties onto the event object
        if ( props ) {
            jQuery.extend( this, props );
        }
    
        // Create a timestamp if incoming event doesn't have one
        this.timeStamp = src && src.timeStamp || Date.now();
    
        // Mark it as fixed
        this[ jQuery.expando ] = true;
    };
    
    // jQuery.Event is based on DOM3 Events as specified by the ECMAScript Language Binding
    // https://www.w3.org/TR/2003/WD-DOM-Level-3-Events-20030331/ecma-script-binding.html
    jQuery.Event.prototype = {
        constructor: jQuery.Event,
        isDefaultPrevented: returnFalse,
        isPropagationStopped: returnFalse,
        isImmediatePropagationStopped: returnFalse,
        isSimulated: false,
    
        preventDefault: function() {
            var e = this.originalEvent;
    
            this.isDefaultPrevented = returnTrue;
    
            if ( e && !this.isSimulated ) {
                e.preventDefault();
            }
        },
        stopPropagation: function() {
            var e = this.originalEvent;
    
            this.isPropagationStopped = returnTrue;
    
            if ( e && !this.isSimulated ) {
                e.stopPropagation();
            }
        },
        stopImmediatePropagation: function() {
            var e = this.originalEvent;
    
            this.isImmediatePropagationStopped = returnTrue;
    
            if ( e && !this.isSimulated ) {
                e.stopImmediatePropagation();
            }
    
            this.stopPropagation();
        }
    };
    
    // Includes all common event props including KeyEvent and MouseEvent specific props
    jQuery.each( {
        altKey: true,
        bubbles: true,
        cancelable: true,
        changedTouches: true,
        ctrlKey: true,
        detail: true,
        eventPhase: true,
        metaKey: true,
        pageX: true,
        pageY: true,
        shiftKey: true,
        view: true,
        "char": true,
        code: true,
        charCode: true,
        key: true,
        keyCode: true,
        button: true,
        buttons: true,
        clientX: true,
        clientY: true,
        offsetX: true,
        offsetY: true,
        pointerId: true,
        pointerType: true,
        screenX: true,
        screenY: true,
        targetTouches: true,
        toElement: true,
        touches: true,
        which: true
    }, jQuery.event.addProp );
    
    jQuery.each( { focus: "focusin", blur: "focusout" }, function( type, delegateType ) {
        jQuery.event.special[ type ] = {
    
            // Utilize native event if possible so blur/focus sequence is correct
            setup: function() {
    
                // Claim the first handler
                // dataPriv.set( this, "focus", ... )
                // dataPriv.set( this, "blur", ... )
                leverageNative( this, type, expectSync );
    
                // Return false to allow normal processing in the caller
                return false;
            },
            trigger: function() {
    
                // Force setup before trigger
                leverageNative( this, type );
    
                // Return non-false to allow normal event-path propagation
                return true;
            },
    
            // Suppress native focus or blur as it's already being fired
            // in leverageNative.
            _default: function() {
                return true;
            },
    
            delegateType: delegateType
        };
    } );
    
    // Create mouseenter/leave events using mouseover/out and event-time checks
    // so that event delegation works in jQuery.
    // Do the same for pointerenter/pointerleave and pointerover/pointerout
    //
    // Support: Safari 7 only
    // Safari sends mouseenter too often; see:
    // https://bugs.chromium.org/p/chromium/issues/detail?id=470258
    // for the description of the bug (it existed in older Chrome versions as well).
    jQuery.each( {
        mouseenter: "mouseover",
        mouseleave: "mouseout",
        pointerenter: "pointerover",
        pointerleave: "pointerout"
    }, function( orig, fix ) {
        jQuery.event.special[ orig ] = {
            delegateType: fix,
            bindType: fix,
    
            handle: function( event ) {
                var ret,
                    target = this,
                    related = event.relatedTarget,
                    handleObj = event.handleObj;
    
                // For mouseenter/leave call the handler if related is outside the target.
                // NB: No relatedTarget if the mouse left/entered the browser window
                if ( !related || ( related !== target && !jQuery.contains( target, related ) ) ) {
                    event.type = handleObj.origType;
                    ret = handleObj.handler.apply( this, arguments );
                    event.type = fix;
                }
                return ret;
            }
        };
    } );
    
    jQuery.fn.extend( {
    
        on: function( types, selector, data, fn ) {
            return on( this, types, selector, data, fn );
        },
        one: function( types, selector, data, fn ) {
            return on( this, types, selector, data, fn, 1 );
        },
        off: function( types, selector, fn ) {
            var handleObj, type;
            if ( types && types.preventDefault && types.handleObj ) {
    
                // ( event )  dispatched jQuery.Event
                handleObj = types.handleObj;
                jQuery( types.delegateTarget ).off(
                    handleObj.namespace ?
                        handleObj.origType + "." + handleObj.namespace :
                        handleObj.origType,
                    handleObj.selector,
                    handleObj.handler
                );
                return this;
            }
            if ( typeof types === "object" ) {
    
                // ( types-object [, selector] )
                for ( type in types ) {
                    this.off( type, selector, types[ type ] );
                }
                return this;
            }
            if ( selector === false || typeof selector === "function" ) {
    
                // ( types [, fn] )
                fn = selector;
                selector = undefined;
            }
            if ( fn === false ) {
                fn = returnFalse;
            }
            return this.each( function() {
                jQuery.event.remove( this, types, fn, selector );
            } );
        }
    } );
    
    
    var
    
        // Support: IE <=10 - 11, Edge 12 - 13 only
        // In IE/Edge using regex groups here causes severe slowdowns.
        // See https://connect.microsoft.com/IE/feedback/details/1736512/
        rnoInnerhtml = /<script|<style|<link/i,
    
        // checked="checked" or checked
        rchecked = /checked\s*(?:[^=]|=\s*.checked.)/i,
        rcleanScript = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g;
    
    // Prefer a tbody over its parent table for containing new rows
    function manipulationTarget( elem, content ) {
        if ( nodeName( elem, "table" ) &&
            nodeName( content.nodeType !== 11 ? content : content.firstChild, "tr" ) ) {
    
            return jQuery( elem ).children( "tbody" )[ 0 ] || elem;
        }
    
        return elem;
    }
    
    // Replace/restore the type attribute of script elements for safe DOM manipulation
    function disableScript( elem ) {
        elem.type = ( elem.getAttribute( "type" ) !== null ) + "/" + elem.type;
        return elem;
    }
    function restoreScript( elem ) {
        if ( ( elem.type || "" ).slice( 0, 5 ) === "true/" ) {
            elem.type = elem.type.slice( 5 );
        } else {
            elem.removeAttribute( "type" );
        }
    
        return elem;
    }
    
    function cloneCopyEvent( src, dest ) {
        var i, l, type, pdataOld, udataOld, udataCur, events;
    
        if ( dest.nodeType !== 1 ) {
            return;
        }
    
        // 1. Copy private data: events, handlers, etc.
        if ( dataPriv.hasData( src ) ) {
            pdataOld = dataPriv.get( src );
            events = pdataOld.events;
    
            if ( events ) {
                dataPriv.remove( dest, "handle events" );
    
                for ( type in events ) {
                    for ( i = 0, l = events[ type ].length; i < l; i++ ) {
                        jQuery.event.add( dest, type, events[ type ][ i ] );
                    }
                }
            }
        }
    
        // 2. Copy user data
        if ( dataUser.hasData( src ) ) {
            udataOld = dataUser.access( src );
            udataCur = jQuery.extend( {}, udataOld );
    
            dataUser.set( dest, udataCur );
        }
    }
    
    // Fix IE bugs, see support tests
    function fixInput( src, dest ) {
        var nodeName = dest.nodeName.toLowerCase();
    
        // Fails to persist the checked state of a cloned checkbox or radio button.
        if ( nodeName === "input" && rcheckableType.test( src.type ) ) {
            dest.checked = src.checked;
    
        // Fails to return the selected option to the default selected state when cloning options
        } else if ( nodeName === "input" || nodeName === "textarea" ) {
            dest.defaultValue = src.defaultValue;
        }
    }
    
    function domManip( collection, args, callback, ignored ) {
    
        // Flatten any nested arrays
        args = flat( args );
    
        var fragment, first, scripts, hasScripts, node, doc,
            i = 0,
            l = collection.length,
            iNoClone = l - 1,
            value = args[ 0 ],
            valueIsFunction = isFunction( value );
    
        // We can't cloneNode fragments that contain checked, in WebKit
        if ( valueIsFunction ||
                ( l > 1 && typeof value === "string" &&
                    !support.checkClone && rchecked.test( value ) ) ) {
            return collection.each( function( index ) {
                var self = collection.eq( index );
                if ( valueIsFunction ) {
                    args[ 0 ] = value.call( this, index, self.html() );
                }
                domManip( self, args, callback, ignored );
            } );
        }
    
        if ( l ) {
            fragment = buildFragment( args, collection[ 0 ].ownerDocument, false, collection, ignored );
            first = fragment.firstChild;
    
            if ( fragment.childNodes.length === 1 ) {
                fragment = first;
            }
    
            // Require either new content or an interest in ignored elements to invoke the callback
            if ( first || ignored ) {
                scripts = jQuery.map( getAll( fragment, "script" ), disableScript );
                hasScripts = scripts.length;
    
                // Use the original fragment for the last item
                // instead of the first because it can end up
                // being emptied incorrectly in certain situations (#8070).
                for ( ; i < l; i++ ) {
                    node = fragment;
    
                    if ( i !== iNoClone ) {
                        node = jQuery.clone( node, true, true );
    
                        // Keep references to cloned scripts for later restoration
                        if ( hasScripts ) {
    
                            // Support: Android <=4.0 only, PhantomJS 1 only
                            // push.apply(_, arraylike) throws on ancient WebKit
                            jQuery.merge( scripts, getAll( node, "script" ) );
                        }
                    }
    
                    callback.call( collection[ i ], node, i );
                }
    
                if ( hasScripts ) {
                    doc = scripts[ scripts.length - 1 ].ownerDocument;
    
                    // Reenable scripts
                    jQuery.map( scripts, restoreScript );
    
                    // Evaluate executable scripts on first document insertion
                    for ( i = 0; i < hasScripts; i++ ) {
                        node = scripts[ i ];
                        if ( rscriptType.test( node.type || "" ) &&
                            !dataPriv.access( node, "globalEval" ) &&
                            jQuery.contains( doc, node ) ) {
    
                            if ( node.src && ( node.type || "" ).toLowerCase()  !== "module" ) {
    
                                // Optional AJAX dependency, but won't run scripts if not present
                                if ( jQuery._evalUrl && !node.noModule ) {
                                    jQuery._evalUrl( node.src, {
                                        nonce: node.nonce || node.getAttribute( "nonce" )
                                    }, doc );
                                }
                            } else {
                                DOMEval( node.textContent.replace( rcleanScript, "" ), node, doc );
                            }
                        }
                    }
                }
            }
        }
    
        return collection;
    }
    
    function remove( elem, selector, keepData ) {
        var node,
            nodes = selector ? jQuery.filter( selector, elem ) : elem,
            i = 0;
    
        for ( ; ( node = nodes[ i ] ) != null; i++ ) {
            if ( !keepData && node.nodeType === 1 ) {
                jQuery.cleanData( getAll( node ) );
            }
    
            if ( node.parentNode ) {
                if ( keepData && isAttached( node ) ) {
                    setGlobalEval( getAll( node, "script" ) );
                }
                node.parentNode.removeChild( node );
            }
        }
    
        return elem;
    }
    
    jQuery.extend( {
        htmlPrefilter: function( html ) {
            return html;
        },
    
        clone: function( elem, dataAndEvents, deepDataAndEvents ) {
            var i, l, srcElements, destElements,
                clone = elem.cloneNode( true ),
                inPage = isAttached( elem );
    
            // Fix IE cloning issues
            if ( !support.noCloneChecked && ( elem.nodeType === 1 || elem.nodeType === 11 ) &&
                    !jQuery.isXMLDoc( elem ) ) {
    
                // We eschew Sizzle here for performance reasons: https://jsperf.com/getall-vs-sizzle/2
                destElements = getAll( clone );
                srcElements = getAll( elem );
    
                for ( i = 0, l = srcElements.length; i < l; i++ ) {
                    fixInput( srcElements[ i ], destElements[ i ] );
                }
            }
    
            // Copy the events from the original to the clone
            if ( dataAndEvents ) {
                if ( deepDataAndEvents ) {
                    srcElements = srcElements || getAll( elem );
                    destElements = destElements || getAll( clone );
    
                    for ( i = 0, l = srcElements.length; i < l; i++ ) {
                        cloneCopyEvent( srcElements[ i ], destElements[ i ] );
                    }
                } else {
                    cloneCopyEvent( elem, clone );
                }
            }
    
            // Preserve script evaluation history
            destElements = getAll( clone, "script" );
            if ( destElements.length > 0 ) {
                setGlobalEval( destElements, !inPage && getAll( elem, "script" ) );
            }
    
            // Return the cloned set
            return clone;
        },
    
        cleanData: function( elems ) {
            var data, elem, type,
                special = jQuery.event.special,
                i = 0;
    
            for ( ; ( elem = elems[ i ] ) !== undefined; i++ ) {
                if ( acceptData( elem ) ) {
                    if ( ( data = elem[ dataPriv.expando ] ) ) {
                        if ( data.events ) {
                            for ( type in data.events ) {
                                if ( special[ type ] ) {
                                    jQuery.event.remove( elem, type );
    
                                // This is a shortcut to avoid jQuery.event.remove's overhead
                                } else {
                                    jQuery.removeEvent( elem, type, data.handle );
                                }
                            }
                        }
    
                        // Support: Chrome <=35 - 45+
                        // Assign undefined instead of using delete, see Data#remove
                        elem[ dataPriv.expando ] = undefined;
                    }
                    if ( elem[ dataUser.expando ] ) {
    
                        // Support: Chrome <=35 - 45+
                        // Assign undefined instead of using delete, see Data#remove
                        elem[ dataUser.expando ] = undefined;
                    }
                }
            }
        }
    } );
    
    jQuery.fn.extend( {
        detach: function( selector ) {
            return remove( this, selector, true );
        },
    
        remove: function( selector ) {
            return remove( this, selector );
        },
    
        text: function( value ) {
            return access( this, function( value ) {
                return value === undefined ?
                    jQuery.text( this ) :
                    this.empty().each( function() {
                        if ( this.nodeType === 1 || this.nodeType === 11 || this.nodeType === 9 ) {
                            this.textContent = value;
                        }
                    } );
            }, null, value, arguments.length );
        },
    
        append: function() {
            return domManip( this, arguments, function( elem ) {
                if ( this.nodeType === 1 || this.nodeType === 11 || this.nodeType === 9 ) {
                    var target = manipulationTarget( this, elem );
                    target.appendChild( elem );
                }
            } );
        },
    
        prepend: function() {
            return domManip( this, arguments, function( elem ) {
                if ( this.nodeType === 1 || this.nodeType === 11 || this.nodeType === 9 ) {
                    var target = manipulationTarget( this, elem );
                    target.insertBefore( elem, target.firstChild );
                }
            } );
        },
    
        before: function() {
            return domManip( this, arguments, function( elem ) {
                if ( this.parentNode ) {
                    this.parentNode.insertBefore( elem, this );
                }
            } );
        },
    
        after: function() {
            return domManip( this, arguments, function( elem ) {
                if ( this.parentNode ) {
                    this.parentNode.insertBefore( elem, this.nextSibling );
                }
            } );
        },
    
        empty: function() {
            var elem,
                i = 0;
    
            for ( ; ( elem = this[ i ] ) != null; i++ ) {
                if ( elem.nodeType === 1 ) {
    
                    // Prevent memory leaks
                    jQuery.cleanData( getAll( elem, false ) );
    
                    // Remove any remaining nodes
                    elem.textContent = "";
                }
            }
    
            return this;
        },
    
        clone: function( dataAndEvents, deepDataAndEvents ) {
            dataAndEvents = dataAndEvents == null ? false : dataAndEvents;
            deepDataAndEvents = deepDataAndEvents == null ? dataAndEvents : deepDataAndEvents;
    
            return this.map( function() {
                return jQuery.clone( this, dataAndEvents, deepDataAndEvents );
            } );
        },
    
        html: function( value ) {
            return access( this, function( value ) {
                var elem = this[ 0 ] || {},
                    i = 0,
                    l = this.length;
    
                if ( value === undefined && elem.nodeType === 1 ) {
                    return elem.innerHTML;
                }
    
                // See if we can take a shortcut and just use innerHTML
                if ( typeof value === "string" && !rnoInnerhtml.test( value ) &&
                    !wrapMap[ ( rtagName.exec( value ) || [ "", "" ] )[ 1 ].toLowerCase() ] ) {
    
                    value = jQuery.htmlPrefilter( value );
    
                    try {
                        for ( ; i < l; i++ ) {
                            elem = this[ i ] || {};
    
                            // Remove element nodes and prevent memory leaks
                            if ( elem.nodeType === 1 ) {
                                jQuery.cleanData( getAll( elem, false ) );
                                elem.innerHTML = value;
                            }
                        }
    
                        elem = 0;
    
                    // If using innerHTML throws an exception, use the fallback method
                    } catch ( e ) {}
                }
    
                if ( elem ) {
                    this.empty().append( value );
                }
            }, null, value, arguments.length );
        },
    
        replaceWith: function() {
            var ignored = [];
    
            // Make the changes, replacing each non-ignored context element with the new content
            return domManip( this, arguments, function( elem ) {
                var parent = this.parentNode;
    
                if ( jQuery.inArray( this, ignored ) < 0 ) {
                    jQuery.cleanData( getAll( this ) );
                    if ( parent ) {
                        parent.replaceChild( elem, this );
                    }
                }
    
            // Force callback invocation
            }, ignored );
        }
    } );
    
    jQuery.each( {
        appendTo: "append",
        prependTo: "prepend",
        insertBefore: "before",
        insertAfter: "after",
        replaceAll: "replaceWith"
    }, function( name, original ) {
        jQuery.fn[ name ] = function( selector ) {
            var elems,
                ret = [],
                insert = jQuery( selector ),
                last = insert.length - 1,
                i = 0;
    
            for ( ; i <= last; i++ ) {
                elems = i === last ? this : this.clone( true );
                jQuery( insert[ i ] )[ original ]( elems );
    
                // Support: Android <=4.0 only, PhantomJS 1 only
                // .get() because push.apply(_, arraylike) throws on ancient WebKit
                push.apply( ret, elems.get() );
            }
    
            return this.pushStack( ret );
        };
    } );
    var rnumnonpx = new RegExp( "^(" + pnum + ")(?!px)[a-z%]+$", "i" );
    
    var getStyles = function( elem ) {
    
            // Support: IE <=11 only, Firefox <=30 (#15098, #14150)
            // IE throws on elements created in popups
            // FF meanwhile throws on frame elements through "defaultView.getComputedStyle"
            var view = elem.ownerDocument.defaultView;
    
            if ( !view || !view.opener ) {
                view = window;
            }
    
            return view.getComputedStyle( elem );
        };
    
    var swap = function( elem, options, callback ) {
        var ret, name,
            old = {};
    
        // Remember the old values, and insert the new ones
        for ( name in options ) {
            old[ name ] = elem.style[ name ];
            elem.style[ name ] = options[ name ];
        }
    
        ret = callback.call( elem );
    
        // Revert the old values
        for ( name in options ) {
            elem.style[ name ] = old[ name ];
        }
    
        return ret;
    };
    
    
    var rboxStyle = new RegExp( cssExpand.join( "|" ), "i" );
    
    
    
    ( function() {
    
        // Executing both pixelPosition & boxSizingReliable tests require only one layout
        // so they're executed at the same time to save the second computation.
        function computeStyleTests() {
    
            // This is a singleton, we need to execute it only once
            if ( !div ) {
                return;
            }
    
            container.style.cssText = "position:absolute;left:-11111px;width:60px;" +
                "margin-top:1px;padding:0;border:0";
            div.style.cssText =
                "position:relative;display:block;box-sizing:border-box;overflow:scroll;" +
                "margin:auto;border:1px;padding:1px;" +
                "width:60%;top:1%";
            documentElement.appendChild( container ).appendChild( div );
    
            var divStyle = window.getComputedStyle( div );
            pixelPositionVal = divStyle.top !== "1%";
    
            // Support: Android 4.0 - 4.3 only, Firefox <=3 - 44
            reliableMarginLeftVal = roundPixelMeasures( divStyle.marginLeft ) === 12;
    
            // Support: Android 4.0 - 4.3 only, Safari <=9.1 - 10.1, iOS <=7.0 - 9.3
            // Some styles come back with percentage values, even though they shouldn't
            div.style.right = "60%";
            pixelBoxStylesVal = roundPixelMeasures( divStyle.right ) === 36;
    
            // Support: IE 9 - 11 only
            // Detect misreporting of content dimensions for box-sizing:border-box elements
            boxSizingReliableVal = roundPixelMeasures( divStyle.width ) === 36;
    
            // Support: IE 9 only
            // Detect overflow:scroll screwiness (gh-3699)
            // Support: Chrome <=64
            // Don't get tricked when zoom affects offsetWidth (gh-4029)
            div.style.position = "absolute";
            scrollboxSizeVal = roundPixelMeasures( div.offsetWidth / 3 ) === 12;
    
            documentElement.removeChild( container );
    
            // Nullify the div so it wouldn't be stored in the memory and
            // it will also be a sign that checks already performed
            div = null;
        }
    
        function roundPixelMeasures( measure ) {
            return Math.round( parseFloat( measure ) );
        }
    
        var pixelPositionVal, boxSizingReliableVal, scrollboxSizeVal, pixelBoxStylesVal,
            reliableTrDimensionsVal, reliableMarginLeftVal,
            container = document.createElement( "div" ),
            div = document.createElement( "div" );
    
        // Finish early in limited (non-browser) environments
        if ( !div.style ) {
            return;
        }
    
        // Support: IE <=9 - 11 only
        // Style of cloned element affects source element cloned (#8908)
        div.style.backgroundClip = "content-box";
        div.cloneNode( true ).style.backgroundClip = "";
        support.clearCloneStyle = div.style.backgroundClip === "content-box";
    
        jQuery.extend( support, {
            boxSizingReliable: function() {
                computeStyleTests();
                return boxSizingReliableVal;
            },
            pixelBoxStyles: function() {
                computeStyleTests();
                return pixelBoxStylesVal;
            },
            pixelPosition: function() {
                computeStyleTests();
                return pixelPositionVal;
            },
            reliableMarginLeft: function() {
                computeStyleTests();
                return reliableMarginLeftVal;
            },
            scrollboxSize: function() {
                computeStyleTests();
                return scrollboxSizeVal;
            },
    
            // Support: IE 9 - 11+, Edge 15 - 18+
            // IE/Edge misreport `getComputedStyle` of table rows with width/height
            // set in CSS while `offset*` properties report correct values.
            // Behavior in IE 9 is more subtle than in newer versions & it passes
            // some versions of this test; make sure not to make it pass there!
            //
            // Support: Firefox 70+
            // Only Firefox includes border widths
            // in computed dimensions. (gh-4529)
            reliableTrDimensions: function() {
                var table, tr, trChild, trStyle;
                if ( reliableTrDimensionsVal == null ) {
                    table = document.createElement( "table" );
                    tr = document.createElement( "tr" );
                    trChild = document.createElement( "div" );
    
                    table.style.cssText = "position:absolute;left:-11111px;border-collapse:separate";
                    tr.style.cssText = "border:1px solid";
    
                    // Support: Chrome 86+
                    // Height set through cssText does not get applied.
                    // Computed height then comes back as 0.
                    tr.style.height = "1px";
                    trChild.style.height = "9px";
    
                    // Support: Android 8 Chrome 86+
                    // In our bodyBackground.html iframe,
                    // display for all div elements is set to "inline",
                    // which causes a problem only in Android 8 Chrome 86.
                    // Ensuring the div is display: block
                    // gets around this issue.
                    trChild.style.display = "block";
    
                    documentElement
                        .appendChild( table )
                        .appendChild( tr )
                        .appendChild( trChild );
    
                    trStyle = window.getComputedStyle( tr );
                    reliableTrDimensionsVal = ( parseInt( trStyle.height, 10 ) +
                        parseInt( trStyle.borderTopWidth, 10 ) +
                        parseInt( trStyle.borderBottomWidth, 10 ) ) === tr.offsetHeight;
    
                    documentElement.removeChild( table );
                }
                return reliableTrDimensionsVal;
            }
        } );
    } )();
    
    
    function curCSS( elem, name, computed ) {
        var width, minWidth, maxWidth, ret,
    
            // Support: Firefox 51+
            // Retrieving style before computed somehow
            // fixes an issue with getting wrong values
            // on detached elements
            style = elem.style;
    
        computed = computed || getStyles( elem );
    
        // getPropertyValue is needed for:
        //   .css('filter') (IE 9 only, #12537)
        //   .css('--customProperty) (#3144)
        if ( computed ) {
            ret = computed.getPropertyValue( name ) || computed[ name ];
    
            if ( ret === "" && !isAttached( elem ) ) {
                ret = jQuery.style( elem, name );
            }
    
            // A tribute to the "awesome hack by Dean Edwards"
            // Android Browser returns percentage for some values,
            // but width seems to be reliably pixels.
            // This is against the CSSOM draft spec:
            // https://drafts.csswg.org/cssom/#resolved-values
            if ( !support.pixelBoxStyles() && rnumnonpx.test( ret ) && rboxStyle.test( name ) ) {
    
                // Remember the original values
                width = style.width;
                minWidth = style.minWidth;
                maxWidth = style.maxWidth;
    
                // Put in the new values to get a computed value out
                style.minWidth = style.maxWidth = style.width = ret;
                ret = computed.width;
    
                // Revert the changed values
                style.width = width;
                style.minWidth = minWidth;
                style.maxWidth = maxWidth;
            }
        }
    
        return ret !== undefined ?
    
            // Support: IE <=9 - 11 only
            // IE returns zIndex value as an integer.
            ret + "" :
            ret;
    }
    
    
    function addGetHookIf( conditionFn, hookFn ) {
    
        // Define the hook, we'll check on the first run if it's really needed.
        return {
            get: function() {
                if ( conditionFn() ) {
    
                    // Hook not needed (or it's not possible to use it due
                    // to missing dependency), remove it.
                    delete this.get;
                    return;
                }
    
                // Hook needed; redefine it so that the support test is not executed again.
                return ( this.get = hookFn ).apply( this, arguments );
            }
        };
    }
    
    
    var cssPrefixes = [ "Webkit", "Moz", "ms" ],
        emptyStyle = document.createElement( "div" ).style,
        vendorProps = {};
    
    // Return a vendor-prefixed property or undefined
    function vendorPropName( name ) {
    
        // Check for vendor prefixed names
        var capName = name[ 0 ].toUpperCase() + name.slice( 1 ),
            i = cssPrefixes.length;
    
        while ( i-- ) {
            name = cssPrefixes[ i ] + capName;
            if ( name in emptyStyle ) {
                return name;
            }
        }
    }
    
    // Return a potentially-mapped jQuery.cssProps or vendor prefixed property
    function finalPropName( name ) {
        var final = jQuery.cssProps[ name ] || vendorProps[ name ];
    
        if ( final ) {
            return final;
        }
        if ( name in emptyStyle ) {
            return name;
        }
        return vendorProps[ name ] = vendorPropName( name ) || name;
    }
    
    
    var
    
        // Swappable if display is none or starts with table
        // except "table", "table-cell", or "table-caption"
        // See here for display values: https://developer.mozilla.org/en-US/docs/CSS/display
        rdisplayswap = /^(none|table(?!-c[ea]).+)/,
        rcustomProp = /^--/,
        cssShow = { position: "absolute", visibility: "hidden", display: "block" },
        cssNormalTransform = {
            letterSpacing: "0",
            fontWeight: "400"
        };
    
    function setPositiveNumber( _elem, value, subtract ) {
    
        // Any relative (+/-) values have already been
        // normalized at this point
        var matches = rcssNum.exec( value );
        return matches ?
    
            // Guard against undefined "subtract", e.g., when used as in cssHooks
            Math.max( 0, matches[ 2 ] - ( subtract || 0 ) ) + ( matches[ 3 ] || "px" ) :
            value;
    }
    
    function boxModelAdjustment( elem, dimension, box, isBorderBox, styles, computedVal ) {
        var i = dimension === "width" ? 1 : 0,
            extra = 0,
            delta = 0;
    
        // Adjustment may not be necessary
        if ( box === ( isBorderBox ? "border" : "content" ) ) {
            return 0;
        }
    
        for ( ; i < 4; i += 2 ) {
    
            // Both box models exclude margin
            if ( box === "margin" ) {
                delta += jQuery.css( elem, box + cssExpand[ i ], true, styles );
            }
    
            // If we get here with a content-box, we're seeking "padding" or "border" or "margin"
            if ( !isBorderBox ) {
    
                // Add padding
                delta += jQuery.css( elem, "padding" + cssExpand[ i ], true, styles );
    
                // For "border" or "margin", add border
                if ( box !== "padding" ) {
                    delta += jQuery.css( elem, "border" + cssExpand[ i ] + "Width", true, styles );
    
                // But still keep track of it otherwise
                } else {
                    extra += jQuery.css( elem, "border" + cssExpand[ i ] + "Width", true, styles );
                }
    
            // If we get here with a border-box (content + padding + border), we're seeking "content" or
            // "padding" or "margin"
            } else {
    
                // For "content", subtract padding
                if ( box === "content" ) {
                    delta -= jQuery.css( elem, "padding" + cssExpand[ i ], true, styles );
                }
    
                // For "content" or "padding", subtract border
                if ( box !== "margin" ) {
                    delta -= jQuery.css( elem, "border" + cssExpand[ i ] + "Width", true, styles );
                }
            }
        }
    
        // Account for positive content-box scroll gutter when requested by providing computedVal
        if ( !isBorderBox && computedVal >= 0 ) {
    
            // offsetWidth/offsetHeight is a rounded sum of content, padding, scroll gutter, and border
            // Assuming integer scroll gutter, subtract the rest and round down
            delta += Math.max( 0, Math.ceil(
                elem[ "offset" + dimension[ 0 ].toUpperCase() + dimension.slice( 1 ) ] -
                computedVal -
                delta -
                extra -
                0.5
    
            // If offsetWidth/offsetHeight is unknown, then we can't determine content-box scroll gutter
            // Use an explicit zero to avoid NaN (gh-3964)
            ) ) || 0;
        }
    
        return delta;
    }
    
    function getWidthOrHeight( elem, dimension, extra ) {
    
        // Start with computed style
        var styles = getStyles( elem ),
    
            // To avoid forcing a reflow, only fetch boxSizing if we need it (gh-4322).
            // Fake content-box until we know it's needed to know the true value.
            boxSizingNeeded = !support.boxSizingReliable() || extra,
            isBorderBox = boxSizingNeeded &&
                jQuery.css( elem, "boxSizing", false, styles ) === "border-box",
            valueIsBorderBox = isBorderBox,
    
            val = curCSS( elem, dimension, styles ),
            offsetProp = "offset" + dimension[ 0 ].toUpperCase() + dimension.slice( 1 );
    
        // Support: Firefox <=54
        // Return a confounding non-pixel value or feign ignorance, as appropriate.
        if ( rnumnonpx.test( val ) ) {
            if ( !extra ) {
                return val;
            }
            val = "auto";
        }
    
    
        // Support: IE 9 - 11 only
        // Use offsetWidth/offsetHeight for when box sizing is unreliable.
        // In those cases, the computed value can be trusted to be border-box.
        if ( ( !support.boxSizingReliable() && isBorderBox ||
    
            // Support: IE 10 - 11+, Edge 15 - 18+
            // IE/Edge misreport `getComputedStyle` of table rows with width/height
            // set in CSS while `offset*` properties report correct values.
            // Interestingly, in some cases IE 9 doesn't suffer from this issue.
            !support.reliableTrDimensions() && nodeName( elem, "tr" ) ||
    
            // Fall back to offsetWidth/offsetHeight when value is "auto"
            // This happens for inline elements with no explicit setting (gh-3571)
            val === "auto" ||
    
            // Support: Android <=4.1 - 4.3 only
            // Also use offsetWidth/offsetHeight for misreported inline dimensions (gh-3602)
            !parseFloat( val ) && jQuery.css( elem, "display", false, styles ) === "inline" ) &&
    
            // Make sure the element is visible & connected
            elem.getClientRects().length ) {
    
            isBorderBox = jQuery.css( elem, "boxSizing", false, styles ) === "border-box";
    
            // Where available, offsetWidth/offsetHeight approximate border box dimensions.
            // Where not available (e.g., SVG), assume unreliable box-sizing and interpret the
            // retrieved value as a content box dimension.
            valueIsBorderBox = offsetProp in elem;
            if ( valueIsBorderBox ) {
                val = elem[ offsetProp ];
            }
        }
    
        // Normalize "" and auto
        val = parseFloat( val ) || 0;
    
        // Adjust for the element's box model
        return ( val +
            boxModelAdjustment(
                elem,
                dimension,
                extra || ( isBorderBox ? "border" : "content" ),
                valueIsBorderBox,
                styles,
    
                // Provide the current computed size to request scroll gutter calculation (gh-3589)
                val
            )
        ) + "px";
    }
    
    jQuery.extend( {
    
        // Add in style property hooks for overriding the default
        // behavior of getting and setting a style property
        cssHooks: {
            opacity: {
                get: function( elem, computed ) {
                    if ( computed ) {
    
                        // We should always get a number back from opacity
                        var ret = curCSS( elem, "opacity" );
                        return ret === "" ? "1" : ret;
                    }
                }
            }
        },
    
        // Don't automatically add "px" to these possibly-unitless properties
        cssNumber: {
            "animationIterationCount": true,
            "columnCount": true,
            "fillOpacity": true,
            "flexGrow": true,
            "flexShrink": true,
            "fontWeight": true,
            "gridArea": true,
            "gridColumn": true,
            "gridColumnEnd": true,
            "gridColumnStart": true,
            "gridRow": true,
            "gridRowEnd": true,
            "gridRowStart": true,
            "lineHeight": true,
            "opacity": true,
            "order": true,
            "orphans": true,
            "widows": true,
            "zIndex": true,
            "zoom": true
        },
    
        // Add in properties whose names you wish to fix before
        // setting or getting the value
        cssProps: {},
    
        // Get and set the style property on a DOM Node
        style: function( elem, name, value, extra ) {
    
            // Don't set styles on text and comment nodes
            if ( !elem || elem.nodeType === 3 || elem.nodeType === 8 || !elem.style ) {
                return;
            }
    
            // Make sure that we're working with the right name
            var ret, type, hooks,
                origName = camelCase( name ),
                isCustomProp = rcustomProp.test( name ),
                style = elem.style;
    
            // Make sure that we're working with the right name. We don't
            // want to query the value if it is a CSS custom property
            // since they are user-defined.
            if ( !isCustomProp ) {
                name = finalPropName( origName );
            }
    
            // Gets hook for the prefixed version, then unprefixed version
            hooks = jQuery.cssHooks[ name ] || jQuery.cssHooks[ origName ];
    
            // Check if we're setting a value
            if ( value !== undefined ) {
                type = typeof value;
    
                // Convert "+=" or "-=" to relative numbers (#7345)
                if ( type === "string" && ( ret = rcssNum.exec( value ) ) && ret[ 1 ] ) {
                    value = adjustCSS( elem, name, ret );
    
                    // Fixes bug #9237
                    type = "number";
                }
    
                // Make sure that null and NaN values aren't set (#7116)
                if ( value == null || value !== value ) {
                    return;
                }
    
                // If a number was passed in, add the unit (except for certain CSS properties)
                // The isCustomProp check can be removed in jQuery 4.0 when we only auto-append
                // "px" to a few hardcoded values.
                if ( type === "number" && !isCustomProp ) {
                    value += ret && ret[ 3 ] || ( jQuery.cssNumber[ origName ] ? "" : "px" );
                }
    
                // background-* props affect original clone's values
                if ( !support.clearCloneStyle && value === "" && name.indexOf( "background" ) === 0 ) {
                    style[ name ] = "inherit";
                }
    
                // If a hook was provided, use that value, otherwise just set the specified value
                if ( !hooks || !( "set" in hooks ) ||
                    ( value = hooks.set( elem, value, extra ) ) !== undefined ) {
    
                    if ( isCustomProp ) {
                        style.setProperty( name, value );
                    } else {
                        style[ name ] = value;
                    }
                }
    
            } else {
    
                // If a hook was provided get the non-computed value from there
                if ( hooks && "get" in hooks &&
                    ( ret = hooks.get( elem, false, extra ) ) !== undefined ) {
    
                    return ret;
                }
    
                // Otherwise just get the value from the style object
                return style[ name ];
            }
        },
    
        css: function( elem, name, extra, styles ) {
            var val, num, hooks,
                origName = camelCase( name ),
                isCustomProp = rcustomProp.test( name );
    
            // Make sure that we're working with the right name. We don't
            // want to modify the value if it is a CSS custom property
            // since they are user-defined.
            if ( !isCustomProp ) {
                name = finalPropName( origName );
            }
    
            // Try prefixed name followed by the unprefixed name
            hooks = jQuery.cssHooks[ name ] || jQuery.cssHooks[ origName ];
    
            // If a hook was provided get the computed value from there
            if ( hooks && "get" in hooks ) {
                val = hooks.get( elem, true, extra );
            }
    
            // Otherwise, if a way to get the computed value exists, use that
            if ( val === undefined ) {
                val = curCSS( elem, name, styles );
            }
    
            // Convert "normal" to computed value
            if ( val === "normal" && name in cssNormalTransform ) {
                val = cssNormalTransform[ name ];
            }
    
            // Make numeric if forced or a qualifier was provided and val looks numeric
            if ( extra === "" || extra ) {
                num = parseFloat( val );
                return extra === true || isFinite( num ) ? num || 0 : val;
            }
    
            return val;
        }
    } );
    
    jQuery.each( [ "height", "width" ], function( _i, dimension ) {
        jQuery.cssHooks[ dimension ] = {
            get: function( elem, computed, extra ) {
                if ( computed ) {
    
                    // Certain elements can have dimension info if we invisibly show them
                    // but it must have a current display style that would benefit
                    return rdisplayswap.test( jQuery.css( elem, "display" ) ) &&
    
                        // Support: Safari 8+
                        // Table columns in Safari have non-zero offsetWidth & zero
                        // getBoundingClientRect().width unless display is changed.
                        // Support: IE <=11 only
                        // Running getBoundingClientRect on a disconnected node
                        // in IE throws an error.
                        ( !elem.getClientRects().length || !elem.getBoundingClientRect().width ) ?
                        swap( elem, cssShow, function() {
                            return getWidthOrHeight( elem, dimension, extra );
                        } ) :
                        getWidthOrHeight( elem, dimension, extra );
                }
            },
    
            set: function( elem, value, extra ) {
                var matches,
                    styles = getStyles( elem ),
    
                    // Only read styles.position if the test has a chance to fail
                    // to avoid forcing a reflow.
                    scrollboxSizeBuggy = !support.scrollboxSize() &&
                        styles.position === "absolute",
    
                    // To avoid forcing a reflow, only fetch boxSizing if we need it (gh-3991)
                    boxSizingNeeded = scrollboxSizeBuggy || extra,
                    isBorderBox = boxSizingNeeded &&
                        jQuery.css( elem, "boxSizing", false, styles ) === "border-box",
                    subtract = extra ?
                        boxModelAdjustment(
                            elem,
                            dimension,
                            extra,
                            isBorderBox,
                            styles
                        ) :
                        0;
    
                // Account for unreliable border-box dimensions by comparing offset* to computed and
                // faking a content-box to get border and padding (gh-3699)
                if ( isBorderBox && scrollboxSizeBuggy ) {
                    subtract -= Math.ceil(
                        elem[ "offset" + dimension[ 0 ].toUpperCase() + dimension.slice( 1 ) ] -
                        parseFloat( styles[ dimension ] ) -
                        boxModelAdjustment( elem, dimension, "border", false, styles ) -
                        0.5
                    );
                }
    
                // Convert to pixels if value adjustment is needed
                if ( subtract && ( matches = rcssNum.exec( value ) ) &&
                    ( matches[ 3 ] || "px" ) !== "px" ) {
    
                    elem.style[ dimension ] = value;
                    value = jQuery.css( elem, dimension );
                }
    
                return setPositiveNumber( elem, value, subtract );
            }
        };
    } );
    
    jQuery.cssHooks.marginLeft = addGetHookIf( support.reliableMarginLeft,
        function( elem, computed ) {
            if ( computed ) {
                return ( parseFloat( curCSS( elem, "marginLeft" ) ) ||
                    elem.getBoundingClientRect().left -
                        swap( elem, { marginLeft: 0 }, function() {
                            return elem.getBoundingClientRect().left;
                        } )
                ) + "px";
            }
        }
    );
    
    // These hooks are used by animate to expand properties
    jQuery.each( {
        margin: "",
        padding: "",
        border: "Width"
    }, function( prefix, suffix ) {
        jQuery.cssHooks[ prefix + suffix ] = {
            expand: function( value ) {
                var i = 0,
                    expanded = {},
    
                    // Assumes a single number if not a string
                    parts = typeof value === "string" ? value.split( " " ) : [ value ];
    
                for ( ; i < 4; i++ ) {
                    expanded[ prefix + cssExpand[ i ] + suffix ] =
                        parts[ i ] || parts[ i - 2 ] || parts[ 0 ];
                }
    
                return expanded;
            }
        };
    
        if ( prefix !== "margin" ) {
            jQuery.cssHooks[ prefix + suffix ].set = setPositiveNumber;
        }
    } );
    
    jQuery.fn.extend( {
        css: function( name, value ) {
            return access( this, function( elem, name, value ) {
                var styles, len,
                    map = {},
                    i = 0;
    
                if ( Array.isArray( name ) ) {
                    styles = getStyles( elem );
                    len = name.length;
    
                    for ( ; i < len; i++ ) {
                        map[ name[ i ] ] = jQuery.css( elem, name[ i ], false, styles );
                    }
    
                    return map;
                }
    
                return value !== undefined ?
                    jQuery.style( elem, name, value ) :
                    jQuery.css( elem, name );
            }, name, value, arguments.length > 1 );
        }
    } );
    
    
    function Tween( elem, options, prop, end, easing ) {
        return new Tween.prototype.init( elem, options, prop, end, easing );
    }
    jQuery.Tween = Tween;
    
    Tween.prototype = {
        constructor: Tween,
        init: function( elem, options, prop, end, easing, unit ) {
            this.elem = elem;
            this.prop = prop;
            this.easing = easing || jQuery.easing._default;
            this.options = options;
            this.start = this.now = this.cur();
            this.end = end;
            this.unit = unit || ( jQuery.cssNumber[ prop ] ? "" : "px" );
        },
        cur: function() {
            var hooks = Tween.propHooks[ this.prop ];
    
            return hooks && hooks.get ?
                hooks.get( this ) :
                Tween.propHooks._default.get( this );
        },
        run: function( percent ) {
            var eased,
                hooks = Tween.propHooks[ this.prop ];
    
            if ( this.options.duration ) {
                this.pos = eased = jQuery.easing[ this.easing ](
                    percent, this.options.duration * percent, 0, 1, this.options.duration
                );
            } else {
                this.pos = eased = percent;
            }
            this.now = ( this.end - this.start ) * eased + this.start;
    
            if ( this.options.step ) {
                this.options.step.call( this.elem, this.now, this );
            }
    
            if ( hooks && hooks.set ) {
                hooks.set( this );
            } else {
                Tween.propHooks._default.set( this );
            }
            return this;
        }
    };
    
    Tween.prototype.init.prototype = Tween.prototype;
    
    Tween.propHooks = {
        _default: {
            get: function( tween ) {
                var result;
    
                // Use a property on the element directly when it is not a DOM element,
                // or when there is no matching style property that exists.
                if ( tween.elem.nodeType !== 1 ||
                    tween.elem[ tween.prop ] != null && tween.elem.style[ tween.prop ] == null ) {
                    return tween.elem[ tween.prop ];
                }
    
                // Passing an empty string as a 3rd parameter to .css will automatically
                // attempt a parseFloat and fallback to a string if the parse fails.
                // Simple values such as "10px" are parsed to Float;
                // complex values such as "rotate(1rad)" are returned as-is.
                result = jQuery.css( tween.elem, tween.prop, "" );
    
                // Empty strings, null, undefined and "auto" are converted to 0.
                return !result || result === "auto" ? 0 : result;
            },
            set: function( tween ) {
    
                // Use step hook for back compat.
                // Use cssHook if its there.
                // Use .style if available and use plain properties where available.
                if ( jQuery.fx.step[ tween.prop ] ) {
                    jQuery.fx.step[ tween.prop ]( tween );
                } else if ( tween.elem.nodeType === 1 && (
                    jQuery.cssHooks[ tween.prop ] ||
                        tween.elem.style[ finalPropName( tween.prop ) ] != null ) ) {
                    jQuery.style( tween.elem, tween.prop, tween.now + tween.unit );
                } else {
                    tween.elem[ tween.prop ] = tween.now;
                }
            }
        }
    };
    
    // Support: IE <=9 only
    // Panic based approach to setting things on disconnected nodes
    Tween.propHooks.scrollTop = Tween.propHooks.scrollLeft = {
        set: function( tween ) {
            if ( tween.elem.nodeType && tween.elem.parentNode ) {
                tween.elem[ tween.prop ] = tween.now;
            }
        }
    };
    
    jQuery.easing = {
        linear: function( p ) {
            return p;
        },
        swing: function( p ) {
            return 0.5 - Math.cos( p * Math.PI ) / 2;
        },
        _default: "swing"
    };
    
    jQuery.fx = Tween.prototype.init;
    
    // Back compat <1.8 extension point
    jQuery.fx.step = {};
    
    
    
    
    var
        fxNow, inProgress,
        rfxtypes = /^(?:toggle|show|hide)$/,
        rrun = /queueHooks$/;
    
    function schedule() {
        if ( inProgress ) {
            if ( document.hidden === false && window.requestAnimationFrame ) {
                window.requestAnimationFrame( schedule );
            } else {
                window.setTimeout( schedule, jQuery.fx.interval );
            }
    
            jQuery.fx.tick();
        }
    }
    
    // Animations created synchronously will run synchronously
    function createFxNow() {
        window.setTimeout( function() {
            fxNow = undefined;
        } );
        return ( fxNow = Date.now() );
    }
    
    // Generate parameters to create a standard animation
    function genFx( type, includeWidth ) {
        var which,
            i = 0,
            attrs = { height: type };
    
        // If we include width, step value is 1 to do all cssExpand values,
        // otherwise step value is 2 to skip over Left and Right
        includeWidth = includeWidth ? 1 : 0;
        for ( ; i < 4; i += 2 - includeWidth ) {
            which = cssExpand[ i ];
            attrs[ "margin" + which ] = attrs[ "padding" + which ] = type;
        }
    
        if ( includeWidth ) {
            attrs.opacity = attrs.width = type;
        }
    
        return attrs;
    }
    
    function createTween( value, prop, animation ) {
        var tween,
            collection = ( Animation.tweeners[ prop ] || [] ).concat( Animation.tweeners[ "*" ] ),
            index = 0,
            length = collection.length;
        for ( ; index < length; index++ ) {
            if ( ( tween = collection[ index ].call( animation, prop, value ) ) ) {
    
                // We're done with this property
                return tween;
            }
        }
    }
    
    function defaultPrefilter( elem, props, opts ) {
        var prop, value, toggle, hooks, oldfire, propTween, restoreDisplay, display,
            isBox = "width" in props || "height" in props,
            anim = this,
            orig = {},
            style = elem.style,
            hidden = elem.nodeType && isHiddenWithinTree( elem ),
            dataShow = dataPriv.get( elem, "fxshow" );
    
        // Queue-skipping animations hijack the fx hooks
        if ( !opts.queue ) {
            hooks = jQuery._queueHooks( elem, "fx" );
            if ( hooks.unqueued == null ) {
                hooks.unqueued = 0;
                oldfire = hooks.empty.fire;
                hooks.empty.fire = function() {
                    if ( !hooks.unqueued ) {
                        oldfire();
                    }
                };
            }
            hooks.unqueued++;
    
            anim.always( function() {
    
                // Ensure the complete handler is called before this completes
                anim.always( function() {
                    hooks.unqueued--;
                    if ( !jQuery.queue( elem, "fx" ).length ) {
                        hooks.empty.fire();
                    }
                } );
            } );
        }
    
        // Detect show/hide animations
        for ( prop in props ) {
            value = props[ prop ];
            if ( rfxtypes.test( value ) ) {
                delete props[ prop ];
                toggle = toggle || value === "toggle";
                if ( value === ( hidden ? "hide" : "show" ) ) {
    
                    // Pretend to be hidden if this is a "show" and
                    // there is still data from a stopped show/hide
                    if ( value === "show" && dataShow && dataShow[ prop ] !== undefined ) {
                        hidden = true;
    
                    // Ignore all other no-op show/hide data
                    } else {
                        continue;
                    }
                }
                orig[ prop ] = dataShow && dataShow[ prop ] || jQuery.style( elem, prop );
            }
        }
    
        // Bail out if this is a no-op like .hide().hide()
        propTween = !jQuery.isEmptyObject( props );
        if ( !propTween && jQuery.isEmptyObject( orig ) ) {
            return;
        }
    
        // Restrict "overflow" and "display" styles during box animations
        if ( isBox && elem.nodeType === 1 ) {
    
            // Support: IE <=9 - 11, Edge 12 - 15
            // Record all 3 overflow attributes because IE does not infer the shorthand
            // from identically-valued overflowX and overflowY and Edge just mirrors
            // the overflowX value there.
            opts.overflow = [ style.overflow, style.overflowX, style.overflowY ];
    
            // Identify a display type, preferring old show/hide data over the CSS cascade
            restoreDisplay = dataShow && dataShow.display;
            if ( restoreDisplay == null ) {
                restoreDisplay = dataPriv.get( elem, "display" );
            }
            display = jQuery.css( elem, "display" );
            if ( display === "none" ) {
                if ( restoreDisplay ) {
                    display = restoreDisplay;
                } else {
    
                    // Get nonempty value(s) by temporarily forcing visibility
                    showHide( [ elem ], true );
                    restoreDisplay = elem.style.display || restoreDisplay;
                    display = jQuery.css( elem, "display" );
                    showHide( [ elem ] );
                }
            }
    
            // Animate inline elements as inline-block
            if ( display === "inline" || display === "inline-block" && restoreDisplay != null ) {
                if ( jQuery.css( elem, "float" ) === "none" ) {
    
                    // Restore the original display value at the end of pure show/hide animations
                    if ( !propTween ) {
                        anim.done( function() {
                            style.display = restoreDisplay;
                        } );
                        if ( restoreDisplay == null ) {
                            display = style.display;
                            restoreDisplay = display === "none" ? "" : display;
                        }
                    }
                    style.display = "inline-block";
                }
            }
        }
    
        if ( opts.overflow ) {
            style.overflow = "hidden";
            anim.always( function() {
                style.overflow = opts.overflow[ 0 ];
                style.overflowX = opts.overflow[ 1 ];
                style.overflowY = opts.overflow[ 2 ];
            } );
        }
    
        // Implement show/hide animations
        propTween = false;
        for ( prop in orig ) {
    
            // General show/hide setup for this element animation
            if ( !propTween ) {
                if ( dataShow ) {
                    if ( "hidden" in dataShow ) {
                        hidden = dataShow.hidden;
                    }
                } else {
                    dataShow = dataPriv.access( elem, "fxshow", { display: restoreDisplay } );
                }
    
                // Store hidden/visible for toggle so `.stop().toggle()` "reverses"
                if ( toggle ) {
                    dataShow.hidden = !hidden;
                }
    
                // Show elements before animating them
                if ( hidden ) {
                    showHide( [ elem ], true );
                }
    
                /* eslint-disable no-loop-func */
    
                anim.done( function() {
    
                    /* eslint-enable no-loop-func */
    
                    // The final step of a "hide" animation is actually hiding the element
                    if ( !hidden ) {
                        showHide( [ elem ] );
                    }
                    dataPriv.remove( elem, "fxshow" );
                    for ( prop in orig ) {
                        jQuery.style( elem, prop, orig[ prop ] );
                    }
                } );
            }
    
            // Per-property setup
            propTween = createTween( hidden ? dataShow[ prop ] : 0, prop, anim );
            if ( !( prop in dataShow ) ) {
                dataShow[ prop ] = propTween.start;
                if ( hidden ) {
                    propTween.end = propTween.start;
                    propTween.start = 0;
                }
            }
        }
    }
    
    function propFilter( props, specialEasing ) {
        var index, name, easing, value, hooks;
    
        // camelCase, specialEasing and expand cssHook pass
        for ( index in props ) {
            name = camelCase( index );
            easing = specialEasing[ name ];
            value = props[ index ];
            if ( Array.isArray( value ) ) {
                easing = value[ 1 ];
                value = props[ index ] = value[ 0 ];
            }
    
            if ( index !== name ) {
                props[ name ] = value;
                delete props[ index ];
            }
    
            hooks = jQuery.cssHooks[ name ];
            if ( hooks && "expand" in hooks ) {
                value = hooks.expand( value );
                delete props[ name ];
    
                // Not quite $.extend, this won't overwrite existing keys.
                // Reusing 'index' because we have the correct "name"
                for ( index in value ) {
                    if ( !( index in props ) ) {
                        props[ index ] = value[ index ];
                        specialEasing[ index ] = easing;
                    }
                }
            } else {
                specialEasing[ name ] = easing;
            }
        }
    }
    
    function Animation( elem, properties, options ) {
        var result,
            stopped,
            index = 0,
            length = Animation.prefilters.length,
            deferred = jQuery.Deferred().always( function() {
    
                // Don't match elem in the :animated selector
                delete tick.elem;
            } ),
            tick = function() {
                if ( stopped ) {
                    return false;
                }
                var currentTime = fxNow || createFxNow(),
                    remaining = Math.max( 0, animation.startTime + animation.duration - currentTime ),
    
                    // Support: Android 2.3 only
                    // Archaic crash bug won't allow us to use `1 - ( 0.5 || 0 )` (#12497)
                    temp = remaining / animation.duration || 0,
                    percent = 1 - temp,
                    index = 0,
                    length = animation.tweens.length;
    
                for ( ; index < length; index++ ) {
                    animation.tweens[ index ].run( percent );
                }
    
                deferred.notifyWith( elem, [ animation, percent, remaining ] );
    
                // If there's more to do, yield
                if ( percent < 1 && length ) {
                    return remaining;
                }
    
                // If this was an empty animation, synthesize a final progress notification
                if ( !length ) {
                    deferred.notifyWith( elem, [ animation, 1, 0 ] );
                }
    
                // Resolve the animation and report its conclusion
                deferred.resolveWith( elem, [ animation ] );
                return false;
            },
            animation = deferred.promise( {
                elem: elem,
                props: jQuery.extend( {}, properties ),
                opts: jQuery.extend( true, {
                    specialEasing: {},
                    easing: jQuery.easing._default
                }, options ),
                originalProperties: properties,
                originalOptions: options,
                startTime: fxNow || createFxNow(),
                duration: options.duration,
                tweens: [],
                createTween: function( prop, end ) {
                    var tween = jQuery.Tween( elem, animation.opts, prop, end,
                        animation.opts.specialEasing[ prop ] || animation.opts.easing );
                    animation.tweens.push( tween );
                    return tween;
                },
                stop: function( gotoEnd ) {
                    var index = 0,
    
                        // If we are going to the end, we want to run all the tweens
                        // otherwise we skip this part
                        length = gotoEnd ? animation.tweens.length : 0;
                    if ( stopped ) {
                        return this;
                    }
                    stopped = true;
                    for ( ; index < length; index++ ) {
                        animation.tweens[ index ].run( 1 );
                    }
    
                    // Resolve when we played the last frame; otherwise, reject
                    if ( gotoEnd ) {
                        deferred.notifyWith( elem, [ animation, 1, 0 ] );
                        deferred.resolveWith( elem, [ animation, gotoEnd ] );
                    } else {
                        deferred.rejectWith( elem, [ animation, gotoEnd ] );
                    }
                    return this;
                }
            } ),
            props = animation.props;
    
        propFilter( props, animation.opts.specialEasing );
    
        for ( ; index < length; index++ ) {
            result = Animation.prefilters[ index ].call( animation, elem, props, animation.opts );
            if ( result ) {
                if ( isFunction( result.stop ) ) {
                    jQuery._queueHooks( animation.elem, animation.opts.queue ).stop =
                        result.stop.bind( result );
                }
                return result;
            }
        }
    
        jQuery.map( props, createTween, animation );
    
        if ( isFunction( animation.opts.start ) ) {
            animation.opts.start.call( elem, animation );
        }
    
        // Attach callbacks from options
        animation
            .progress( animation.opts.progress )
            .done( animation.opts.done, animation.opts.complete )
            .fail( animation.opts.fail )
            .always( animation.opts.always );
    
        jQuery.fx.timer(
            jQuery.extend( tick, {
                elem: elem,
                anim: animation,
                queue: animation.opts.queue
            } )
        );
    
        return animation;
    }
    
    jQuery.Animation = jQuery.extend( Animation, {
    
        tweeners: {
            "*": [ function( prop, value ) {
                var tween = this.createTween( prop, value );
                adjustCSS( tween.elem, prop, rcssNum.exec( value ), tween );
                return tween;
            } ]
        },
    
        tweener: function( props, callback ) {
            if ( isFunction( props ) ) {
                callback = props;
                props = [ "*" ];
            } else {
                props = props.match( rnothtmlwhite );
            }
    
            var prop,
                index = 0,
                length = props.length;
    
            for ( ; index < length; index++ ) {
                prop = props[ index ];
                Animation.tweeners[ prop ] = Animation.tweeners[ prop ] || [];
                Animation.tweeners[ prop ].unshift( callback );
            }
        },
    
        prefilters: [ defaultPrefilter ],
    
        prefilter: function( callback, prepend ) {
            if ( prepend ) {
                Animation.prefilters.unshift( callback );
            } else {
                Animation.prefilters.push( callback );
            }
        }
    } );
    
    jQuery.speed = function( speed, easing, fn ) {
        var opt = speed && typeof speed === "object" ? jQuery.extend( {}, speed ) : {
            complete: fn || !fn && easing ||
                isFunction( speed ) && speed,
            duration: speed,
            easing: fn && easing || easing && !isFunction( easing ) && easing
        };
    
        // Go to the end state if fx are off
        if ( jQuery.fx.off ) {
            opt.duration = 0;
    
        } else {
            if ( typeof opt.duration !== "number" ) {
                if ( opt.duration in jQuery.fx.speeds ) {
                    opt.duration = jQuery.fx.speeds[ opt.duration ];
    
                } else {
                    opt.duration = jQuery.fx.speeds._default;
                }
            }
        }
    
        // Normalize opt.queue - true/undefined/null -> "fx"
        if ( opt.queue == null || opt.queue === true ) {
            opt.queue = "fx";
        }
    
        // Queueing
        opt.old = opt.complete;
    
        opt.complete = function() {
            if ( isFunction( opt.old ) ) {
                opt.old.call( this );
            }
    
            if ( opt.queue ) {
                jQuery.dequeue( this, opt.queue );
            }
        };
    
        return opt;
    };
    
    jQuery.fn.extend( {
        fadeTo: function( speed, to, easing, callback ) {
    
            // Show any hidden elements after setting opacity to 0
            return this.filter( isHiddenWithinTree ).css( "opacity", 0 ).show()
    
                // Animate to the value specified
                .end().animate( { opacity: to }, speed, easing, callback );
        },
        animate: function( prop, speed, easing, callback ) {
            var empty = jQuery.isEmptyObject( prop ),
                optall = jQuery.speed( speed, easing, callback ),
                doAnimation = function() {
    
                    // Operate on a copy of prop so per-property easing won't be lost
                    var anim = Animation( this, jQuery.extend( {}, prop ), optall );
    
                    // Empty animations, or finishing resolves immediately
                    if ( empty || dataPriv.get( this, "finish" ) ) {
                        anim.stop( true );
                    }
                };
    
            doAnimation.finish = doAnimation;
    
            return empty || optall.queue === false ?
                this.each( doAnimation ) :
                this.queue( optall.queue, doAnimation );
        },
        stop: function( type, clearQueue, gotoEnd ) {
            var stopQueue = function( hooks ) {
                var stop = hooks.stop;
                delete hooks.stop;
                stop( gotoEnd );
            };
    
            if ( typeof type !== "string" ) {
                gotoEnd = clearQueue;
                clearQueue = type;
                type = undefined;
            }
            if ( clearQueue ) {
                this.queue( type || "fx", [] );
            }
    
            return this.each( function() {
                var dequeue = true,
                    index = type != null && type + "queueHooks",
                    timers = jQuery.timers,
                    data = dataPriv.get( this );
    
                if ( index ) {
                    if ( data[ index ] && data[ index ].stop ) {
                        stopQueue( data[ index ] );
                    }
                } else {
                    for ( index in data ) {
                        if ( data[ index ] && data[ index ].stop && rrun.test( index ) ) {
                            stopQueue( data[ index ] );
                        }
                    }
                }
    
                for ( index = timers.length; index--; ) {
                    if ( timers[ index ].elem === this &&
                        ( type == null || timers[ index ].queue === type ) ) {
    
                        timers[ index ].anim.stop( gotoEnd );
                        dequeue = false;
                        timers.splice( index, 1 );
                    }
                }
    
                // Start the next in the queue if the last step wasn't forced.
                // Timers currently will call their complete callbacks, which
                // will dequeue but only if they were gotoEnd.
                if ( dequeue || !gotoEnd ) {
                    jQuery.dequeue( this, type );
                }
            } );
        },
        finish: function( type ) {
            if ( type !== false ) {
                type = type || "fx";
            }
            return this.each( function() {
                var index,
                    data = dataPriv.get( this ),
                    queue = data[ type + "queue" ],
                    hooks = data[ type + "queueHooks" ],
                    timers = jQuery.timers,
                    length = queue ? queue.length : 0;
    
                // Enable finishing flag on private data
                data.finish = true;
    
                // Empty the queue first
                jQuery.queue( this, type, [] );
    
                if ( hooks && hooks.stop ) {
                    hooks.stop.call( this, true );
                }
    
                // Look for any active animations, and finish them
                for ( index = timers.length; index--; ) {
                    if ( timers[ index ].elem === this && timers[ index ].queue === type ) {
                        timers[ index ].anim.stop( true );
                        timers.splice( index, 1 );
                    }
                }
    
                // Look for any animations in the old queue and finish them
                for ( index = 0; index < length; index++ ) {
                    if ( queue[ index ] && queue[ index ].finish ) {
                        queue[ index ].finish.call( this );
                    }
                }
    
                // Turn off finishing flag
                delete data.finish;
            } );
        }
    } );
    
    jQuery.each( [ "toggle", "show", "hide" ], function( _i, name ) {
        var cssFn = jQuery.fn[ name ];
        jQuery.fn[ name ] = function( speed, easing, callback ) {
            return speed == null || typeof speed === "boolean" ?
                cssFn.apply( this, arguments ) :
                this.animate( genFx( name, true ), speed, easing, callback );
        };
    } );
    
    // Generate shortcuts for custom animations
    jQuery.each( {
        slideDown: genFx( "show" ),
        slideUp: genFx( "hide" ),
        slideToggle: genFx( "toggle" ),
        fadeIn: { opacity: "show" },
        fadeOut: { opacity: "hide" },
        fadeToggle: { opacity: "toggle" }
    }, function( name, props ) {
        jQuery.fn[ name ] = function( speed, easing, callback ) {
            return this.animate( props, speed, easing, callback );
        };
    } );
    
    jQuery.timers = [];
    jQuery.fx.tick = function() {
        var timer,
            i = 0,
            timers = jQuery.timers;
    
        fxNow = Date.now();
    
        for ( ; i < timers.length; i++ ) {
            timer = timers[ i ];
    
            // Run the timer and safely remove it when done (allowing for external removal)
            if ( !timer() && timers[ i ] === timer ) {
                timers.splice( i--, 1 );
            }
        }
    
        if ( !timers.length ) {
            jQuery.fx.stop();
        }
        fxNow = undefined;
    };
    
    jQuery.fx.timer = function( timer ) {
        jQuery.timers.push( timer );
        jQuery.fx.start();
    };
    
    jQuery.fx.interval = 13;
    jQuery.fx.start = function() {
        if ( inProgress ) {
            return;
        }
    
        inProgress = true;
        schedule();
    };
    
    jQuery.fx.stop = function() {
        inProgress = null;
    };
    
    jQuery.fx.speeds = {
        slow: 600,
        fast: 200,
    
        // Default speed
        _default: 400
    };
    
    
    // Based off of the plugin by Clint Helfers, with permission.
    // https://web.archive.org/web/20100324014747/http://blindsignals.com/index.php/2009/07/jquery-delay/
    jQuery.fn.delay = function( time, type ) {
        time = jQuery.fx ? jQuery.fx.speeds[ time ] || time : time;
        type = type || "fx";
    
        return this.queue( type, function( next, hooks ) {
            var timeout = window.setTimeout( next, time );
            hooks.stop = function() {
                window.clearTimeout( timeout );
            };
        } );
    };
    
    
    ( function() {
        var input = document.createElement( "input" ),
            select = document.createElement( "select" ),
            opt = select.appendChild( document.createElement( "option" ) );
    
        input.type = "checkbox";
    
        // Support: Android <=4.3 only
        // Default value for a checkbox should be "on"
        support.checkOn = input.value !== "";
    
        // Support: IE <=11 only
        // Must access selectedIndex to make default options select
        support.optSelected = opt.selected;
    
        // Support: IE <=11 only
        // An input loses its value after becoming a radio
        input = document.createElement( "input" );
        input.value = "t";
        input.type = "radio";
        support.radioValue = input.value === "t";
    } )();
    
    
    var boolHook,
        attrHandle = jQuery.expr.attrHandle;
    
    jQuery.fn.extend( {
        attr: function( name, value ) {
            return access( this, jQuery.attr, name, value, arguments.length > 1 );
        },
    
        removeAttr: function( name ) {
            return this.each( function() {
                jQuery.removeAttr( this, name );
            } );
        }
    } );
    
    jQuery.extend( {
        attr: function( elem, name, value ) {
            var ret, hooks,
                nType = elem.nodeType;
    
            // Don't get/set attributes on text, comment and attribute nodes
            if ( nType === 3 || nType === 8 || nType === 2 ) {
                return;
            }
    
            // Fallback to prop when attributes are not supported
            if ( typeof elem.getAttribute === "undefined" ) {
                return jQuery.prop( elem, name, value );
            }
    
            // Attribute hooks are determined by the lowercase version
            // Grab necessary hook if one is defined
            if ( nType !== 1 || !jQuery.isXMLDoc( elem ) ) {
                hooks = jQuery.attrHooks[ name.toLowerCase() ] ||
                    ( jQuery.expr.match.bool.test( name ) ? boolHook : undefined );
            }
    
            if ( value !== undefined ) {
                if ( value === null ) {
                    jQuery.removeAttr( elem, name );
                    return;
                }
    
                if ( hooks && "set" in hooks &&
                    ( ret = hooks.set( elem, value, name ) ) !== undefined ) {
                    return ret;
                }
    
                elem.setAttribute( name, value + "" );
                return value;
            }
    
            if ( hooks && "get" in hooks && ( ret = hooks.get( elem, name ) ) !== null ) {
                return ret;
            }
    
            ret = jQuery.find.attr( elem, name );
    
            // Non-existent attributes return null, we normalize to undefined
            return ret == null ? undefined : ret;
        },
    
        attrHooks: {
            type: {
                set: function( elem, value ) {
                    if ( !support.radioValue && value === "radio" &&
                        nodeName( elem, "input" ) ) {
                        var val = elem.value;
                        elem.setAttribute( "type", value );
                        if ( val ) {
                            elem.value = val;
                        }
                        return value;
                    }
                }
            }
        },
    
        removeAttr: function( elem, value ) {
            var name,
                i = 0,
    
                // Attribute names can contain non-HTML whitespace characters
                // https://html.spec.whatwg.org/multipage/syntax.html#attributes-2
                attrNames = value && value.match( rnothtmlwhite );
    
            if ( attrNames && elem.nodeType === 1 ) {
                while ( ( name = attrNames[ i++ ] ) ) {
                    elem.removeAttribute( name );
                }
            }
        }
    } );
    
    // Hooks for boolean attributes
    boolHook = {
        set: function( elem, value, name ) {
            if ( value === false ) {
    
                // Remove boolean attributes when set to false
                jQuery.removeAttr( elem, name );
            } else {
                elem.setAttribute( name, name );
            }
            return name;
        }
    };
    
    jQuery.each( jQuery.expr.match.bool.source.match( /\w+/g ), function( _i, name ) {
        var getter = attrHandle[ name ] || jQuery.find.attr;
    
        attrHandle[ name ] = function( elem, name, isXML ) {
            var ret, handle,
                lowercaseName = name.toLowerCase();
    
            if ( !isXML ) {
    
                // Avoid an infinite loop by temporarily removing this function from the getter
                handle = attrHandle[ lowercaseName ];
                attrHandle[ lowercaseName ] = ret;
                ret = getter( elem, name, isXML ) != null ?
                    lowercaseName :
                    null;
                attrHandle[ lowercaseName ] = handle;
            }
            return ret;
        };
    } );
    
    
    
    
    var rfocusable = /^(?:input|select|textarea|button)$/i,
        rclickable = /^(?:a|area)$/i;
    
    jQuery.fn.extend( {
        prop: function( name, value ) {
            return access( this, jQuery.prop, name, value, arguments.length > 1 );
        },
    
        removeProp: function( name ) {
            return this.each( function() {
                delete this[ jQuery.propFix[ name ] || name ];
            } );
        }
    } );
    
    jQuery.extend( {
        prop: function( elem, name, value ) {
            var ret, hooks,
                nType = elem.nodeType;
    
            // Don't get/set properties on text, comment and attribute nodes
            if ( nType === 3 || nType === 8 || nType === 2 ) {
                return;
            }
    
            if ( nType !== 1 || !jQuery.isXMLDoc( elem ) ) {
    
                // Fix name and attach hooks
                name = jQuery.propFix[ name ] || name;
                hooks = jQuery.propHooks[ name ];
            }
    
            if ( value !== undefined ) {
                if ( hooks && "set" in hooks &&
                    ( ret = hooks.set( elem, value, name ) ) !== undefined ) {
                    return ret;
                }
    
                return ( elem[ name ] = value );
            }
    
            if ( hooks && "get" in hooks && ( ret = hooks.get( elem, name ) ) !== null ) {
                return ret;
            }
    
            return elem[ name ];
        },
    
        propHooks: {
            tabIndex: {
                get: function( elem ) {
    
                    // Support: IE <=9 - 11 only
                    // elem.tabIndex doesn't always return the
                    // correct value when it hasn't been explicitly set
                    // https://web.archive.org/web/20141116233347/http://fluidproject.org/blog/2008/01/09/getting-setting-and-removing-tabindex-values-with-javascript/
                    // Use proper attribute retrieval(#12072)
                    var tabindex = jQuery.find.attr( elem, "tabindex" );
    
                    if ( tabindex ) {
                        return parseInt( tabindex, 10 );
                    }
    
                    if (
                        rfocusable.test( elem.nodeName ) ||
                        rclickable.test( elem.nodeName ) &&
                        elem.href
                    ) {
                        return 0;
                    }
    
                    return -1;
                }
            }
        },
    
        propFix: {
            "for": "htmlFor",
            "class": "className"
        }
    } );
    
    // Support: IE <=11 only
    // Accessing the selectedIndex property
    // forces the browser to respect setting selected
    // on the option
    // The getter ensures a default option is selected
    // when in an optgroup
    // eslint rule "no-unused-expressions" is disabled for this code
    // since it considers such accessions noop
    if ( !support.optSelected ) {
        jQuery.propHooks.selected = {
            get: function( elem ) {
    
                /* eslint no-unused-expressions: "off" */
    
                var parent = elem.parentNode;
                if ( parent && parent.parentNode ) {
                    parent.parentNode.selectedIndex;
                }
                return null;
            },
            set: function( elem ) {
    
                /* eslint no-unused-expressions: "off" */
    
                var parent = elem.parentNode;
                if ( parent ) {
                    parent.selectedIndex;
    
                    if ( parent.parentNode ) {
                        parent.parentNode.selectedIndex;
                    }
                }
            }
        };
    }
    
    jQuery.each( [
        "tabIndex",
        "readOnly",
        "maxLength",
        "cellSpacing",
        "cellPadding",
        "rowSpan",
        "colSpan",
        "useMap",
        "frameBorder",
        "contentEditable"
    ], function() {
        jQuery.propFix[ this.toLowerCase() ] = this;
    } );
    
    
    
    
        // Strip and collapse whitespace according to HTML spec
        // https://infra.spec.whatwg.org/#strip-and-collapse-ascii-whitespace
        function stripAndCollapse( value ) {
            var tokens = value.match( rnothtmlwhite ) || [];
            return tokens.join( " " );
        }
    
    
    function getClass( elem ) {
        return elem.getAttribute && elem.getAttribute( "class" ) || "";
    }
    
    function classesToArray( value ) {
        if ( Array.isArray( value ) ) {
            return value;
        }
        if ( typeof value === "string" ) {
            return value.match( rnothtmlwhite ) || [];
        }
        return [];
    }
    
    jQuery.fn.extend( {
        addClass: function( value ) {
            var classes, elem, cur, curValue, clazz, j, finalValue,
                i = 0;
    
            if ( isFunction( value ) ) {
                return this.each( function( j ) {
                    jQuery( this ).addClass( value.call( this, j, getClass( this ) ) );
                } );
            }
    
            classes = classesToArray( value );
    
            if ( classes.length ) {
                while ( ( elem = this[ i++ ] ) ) {
                    curValue = getClass( elem );
                    cur = elem.nodeType === 1 && ( " " + stripAndCollapse( curValue ) + " " );
    
                    if ( cur ) {
                        j = 0;
                        while ( ( clazz = classes[ j++ ] ) ) {
                            if ( cur.indexOf( " " + clazz + " " ) < 0 ) {
                                cur += clazz + " ";
                            }
                        }
    
                        // Only assign if different to avoid unneeded rendering.
                        finalValue = stripAndCollapse( cur );
                        if ( curValue !== finalValue ) {
                            elem.setAttribute( "class", finalValue );
                        }
                    }
                }
            }
    
            return this;
        },
    
        removeClass: function( value ) {
            var classes, elem, cur, curValue, clazz, j, finalValue,
                i = 0;
    
            if ( isFunction( value ) ) {
                return this.each( function( j ) {
                    jQuery( this ).removeClass( value.call( this, j, getClass( this ) ) );
                } );
            }
    
            if ( !arguments.length ) {
                return this.attr( "class", "" );
            }
    
            classes = classesToArray( value );
    
            if ( classes.length ) {
                while ( ( elem = this[ i++ ] ) ) {
                    curValue = getClass( elem );
    
                    // This expression is here for better compressibility (see addClass)
                    cur = elem.nodeType === 1 && ( " " + stripAndCollapse( curValue ) + " " );
    
                    if ( cur ) {
                        j = 0;
                        while ( ( clazz = classes[ j++ ] ) ) {
    
                            // Remove *all* instances
                            while ( cur.indexOf( " " + clazz + " " ) > -1 ) {
                                cur = cur.replace( " " + clazz + " ", " " );
                            }
                        }
    
                        // Only assign if different to avoid unneeded rendering.
                        finalValue = stripAndCollapse( cur );
                        if ( curValue !== finalValue ) {
                            elem.setAttribute( "class", finalValue );
                        }
                    }
                }
            }
    
            return this;
        },
    
        toggleClass: function( value, stateVal ) {
            var type = typeof value,
                isValidValue = type === "string" || Array.isArray( value );
    
            if ( typeof stateVal === "boolean" && isValidValue ) {
                return stateVal ? this.addClass( value ) : this.removeClass( value );
            }
    
            if ( isFunction( value ) ) {
                return this.each( function( i ) {
                    jQuery( this ).toggleClass(
                        value.call( this, i, getClass( this ), stateVal ),
                        stateVal
                    );
                } );
            }
    
            return this.each( function() {
                var className, i, self, classNames;
    
                if ( isValidValue ) {
    
                    // Toggle individual class names
                    i = 0;
                    self = jQuery( this );
                    classNames = classesToArray( value );
    
                    while ( ( className = classNames[ i++ ] ) ) {
    
                        // Check each className given, space separated list
                        if ( self.hasClass( className ) ) {
                            self.removeClass( className );
                        } else {
                            self.addClass( className );
                        }
                    }
    
                // Toggle whole class name
                } else if ( value === undefined || type === "boolean" ) {
                    className = getClass( this );
                    if ( className ) {
    
                        // Store className if set
                        dataPriv.set( this, "__className__", className );
                    }
    
                    // If the element has a class name or if we're passed `false`,
                    // then remove the whole classname (if there was one, the above saved it).
                    // Otherwise bring back whatever was previously saved (if anything),
                    // falling back to the empty string if nothing was stored.
                    if ( this.setAttribute ) {
                        this.setAttribute( "class",
                            className || value === false ?
                                "" :
                                dataPriv.get( this, "__className__" ) || ""
                        );
                    }
                }
            } );
        },
    
        hasClass: function( selector ) {
            var className, elem,
                i = 0;
    
            className = " " + selector + " ";
            while ( ( elem = this[ i++ ] ) ) {
                if ( elem.nodeType === 1 &&
                    ( " " + stripAndCollapse( getClass( elem ) ) + " " ).indexOf( className ) > -1 ) {
                    return true;
                }
            }
    
            return false;
        }
    } );
    
    
    
    
    var rreturn = /\r/g;
    
    jQuery.fn.extend( {
        val: function( value ) {
            var hooks, ret, valueIsFunction,
                elem = this[ 0 ];
    
            if ( !arguments.length ) {
                if ( elem ) {
                    hooks = jQuery.valHooks[ elem.type ] ||
                        jQuery.valHooks[ elem.nodeName.toLowerCase() ];
    
                    if ( hooks &&
                        "get" in hooks &&
                        ( ret = hooks.get( elem, "value" ) ) !== undefined
                    ) {
                        return ret;
                    }
    
                    ret = elem.value;
    
                    // Handle most common string cases
                    if ( typeof ret === "string" ) {
                        return ret.replace( rreturn, "" );
                    }
    
                    // Handle cases where value is null/undef or number
                    return ret == null ? "" : ret;
                }
    
                return;
            }
    
            valueIsFunction = isFunction( value );
    
            return this.each( function( i ) {
                var val;
    
                if ( this.nodeType !== 1 ) {
                    return;
                }
    
                if ( valueIsFunction ) {
                    val = value.call( this, i, jQuery( this ).val() );
                } else {
                    val = value;
                }
    
                // Treat null/undefined as ""; convert numbers to string
                if ( val == null ) {
                    val = "";
    
                } else if ( typeof val === "number" ) {
                    val += "";
    
                } else if ( Array.isArray( val ) ) {
                    val = jQuery.map( val, function( value ) {
                        return value == null ? "" : value + "";
                    } );
                }
    
                hooks = jQuery.valHooks[ this.type ] || jQuery.valHooks[ this.nodeName.toLowerCase() ];
    
                // If set returns undefined, fall back to normal setting
                if ( !hooks || !( "set" in hooks ) || hooks.set( this, val, "value" ) === undefined ) {
                    this.value = val;
                }
            } );
        }
    } );
    
    jQuery.extend( {
        valHooks: {
            option: {
                get: function( elem ) {
    
                    var val = jQuery.find.attr( elem, "value" );
                    return val != null ?
                        val :
    
                        // Support: IE <=10 - 11 only
                        // option.text throws exceptions (#14686, #14858)
                        // Strip and collapse whitespace
                        // https://html.spec.whatwg.org/#strip-and-collapse-whitespace
                        stripAndCollapse( jQuery.text( elem ) );
                }
            },
            select: {
                get: function( elem ) {
                    var value, option, i,
                        options = elem.options,
                        index = elem.selectedIndex,
                        one = elem.type === "select-one",
                        values = one ? null : [],
                        max = one ? index + 1 : options.length;
    
                    if ( index < 0 ) {
                        i = max;
    
                    } else {
                        i = one ? index : 0;
                    }
    
                    // Loop through all the selected options
                    for ( ; i < max; i++ ) {
                        option = options[ i ];
    
                        // Support: IE <=9 only
                        // IE8-9 doesn't update selected after form reset (#2551)
                        if ( ( option.selected || i === index ) &&
    
                                // Don't return options that are disabled or in a disabled optgroup
                                !option.disabled &&
                                ( !option.parentNode.disabled ||
                                    !nodeName( option.parentNode, "optgroup" ) ) ) {
    
                            // Get the specific value for the option
                            value = jQuery( option ).val();
    
                            // We don't need an array for one selects
                            if ( one ) {
                                return value;
                            }
    
                            // Multi-Selects return an array
                            values.push( value );
                        }
                    }
    
                    return values;
                },
    
                set: function( elem, value ) {
                    var optionSet, option,
                        options = elem.options,
                        values = jQuery.makeArray( value ),
                        i = options.length;
    
                    while ( i-- ) {
                        option = options[ i ];
    
                        /* eslint-disable no-cond-assign */
    
                        if ( option.selected =
                            jQuery.inArray( jQuery.valHooks.option.get( option ), values ) > -1
                        ) {
                            optionSet = true;
                        }
    
                        /* eslint-enable no-cond-assign */
                    }
    
                    // Force browsers to behave consistently when non-matching value is set
                    if ( !optionSet ) {
                        elem.selectedIndex = -1;
                    }
                    return values;
                }
            }
        }
    } );
    
    // Radios and checkboxes getter/setter
    jQuery.each( [ "radio", "checkbox" ], function() {
        jQuery.valHooks[ this ] = {
            set: function( elem, value ) {
                if ( Array.isArray( value ) ) {
                    return ( elem.checked = jQuery.inArray( jQuery( elem ).val(), value ) > -1 );
                }
            }
        };
        if ( !support.checkOn ) {
            jQuery.valHooks[ this ].get = function( elem ) {
                return elem.getAttribute( "value" ) === null ? "on" : elem.value;
            };
        }
    } );
    
    
    
    
    // Return jQuery for attributes-only inclusion
    
    
    support.focusin = "onfocusin" in window;
    
    
    var rfocusMorph = /^(?:focusinfocus|focusoutblur)$/,
        stopPropagationCallback = function( e ) {
            e.stopPropagation();
        };
    
    jQuery.extend( jQuery.event, {
    
        trigger: function( event, data, elem, onlyHandlers ) {
    
            var i, cur, tmp, bubbleType, ontype, handle, special, lastElement,
                eventPath = [ elem || document ],
                type = hasOwn.call( event, "type" ) ? event.type : event,
                namespaces = hasOwn.call( event, "namespace" ) ? event.namespace.split( "." ) : [];
    
            cur = lastElement = tmp = elem = elem || document;
    
            // Don't do events on text and comment nodes
            if ( elem.nodeType === 3 || elem.nodeType === 8 ) {
                return;
            }
    
            // focus/blur morphs to focusin/out; ensure we're not firing them right now
            if ( rfocusMorph.test( type + jQuery.event.triggered ) ) {
                return;
            }
    
            if ( type.indexOf( "." ) > -1 ) {
    
                // Namespaced trigger; create a regexp to match event type in handle()
                namespaces = type.split( "." );
                type = namespaces.shift();
                namespaces.sort();
            }
            ontype = type.indexOf( ":" ) < 0 && "on" + type;
    
            // Caller can pass in a jQuery.Event object, Object, or just an event type string
            event = event[ jQuery.expando ] ?
                event :
                new jQuery.Event( type, typeof event === "object" && event );
    
            // Trigger bitmask: & 1 for native handlers; & 2 for jQuery (always true)
            event.isTrigger = onlyHandlers ? 2 : 3;
            event.namespace = namespaces.join( "." );
            event.rnamespace = event.namespace ?
                new RegExp( "(^|\\.)" + namespaces.join( "\\.(?:.*\\.|)" ) + "(\\.|$)" ) :
                null;
    
            // Clean up the event in case it is being reused
            event.result = undefined;
            if ( !event.target ) {
                event.target = elem;
            }
    
            // Clone any incoming data and prepend the event, creating the handler arg list
            data = data == null ?
                [ event ] :
                jQuery.makeArray( data, [ event ] );
    
            // Allow special events to draw outside the lines
            special = jQuery.event.special[ type ] || {};
            if ( !onlyHandlers && special.trigger && special.trigger.apply( elem, data ) === false ) {
                return;
            }
    
            // Determine event propagation path in advance, per W3C events spec (#9951)
            // Bubble up to document, then to window; watch for a global ownerDocument var (#9724)
            if ( !onlyHandlers && !special.noBubble && !isWindow( elem ) ) {
    
                bubbleType = special.delegateType || type;
                if ( !rfocusMorph.test( bubbleType + type ) ) {
                    cur = cur.parentNode;
                }
                for ( ; cur; cur = cur.parentNode ) {
                    eventPath.push( cur );
                    tmp = cur;
                }
    
                // Only add window if we got to document (e.g., not plain obj or detached DOM)
                if ( tmp === ( elem.ownerDocument || document ) ) {
                    eventPath.push( tmp.defaultView || tmp.parentWindow || window );
                }
            }
    
            // Fire handlers on the event path
            i = 0;
            while ( ( cur = eventPath[ i++ ] ) && !event.isPropagationStopped() ) {
                lastElement = cur;
                event.type = i > 1 ?
                    bubbleType :
                    special.bindType || type;
    
                // jQuery handler
                handle = ( dataPriv.get( cur, "events" ) || Object.create( null ) )[ event.type ] &&
                    dataPriv.get( cur, "handle" );
                if ( handle ) {
                    handle.apply( cur, data );
                }
    
                // Native handler
                handle = ontype && cur[ ontype ];
                if ( handle && handle.apply && acceptData( cur ) ) {
                    event.result = handle.apply( cur, data );
                    if ( event.result === false ) {
                        event.preventDefault();
                    }
                }
            }
            event.type = type;
    
            // If nobody prevented the default action, do it now
            if ( !onlyHandlers && !event.isDefaultPrevented() ) {
    
                if ( ( !special._default ||
                    special._default.apply( eventPath.pop(), data ) === false ) &&
                    acceptData( elem ) ) {
    
                    // Call a native DOM method on the target with the same name as the event.
                    // Don't do default actions on window, that's where global variables be (#6170)
                    if ( ontype && isFunction( elem[ type ] ) && !isWindow( elem ) ) {
    
                        // Don't re-trigger an onFOO event when we call its FOO() method
                        tmp = elem[ ontype ];
    
                        if ( tmp ) {
                            elem[ ontype ] = null;
                        }
    
                        // Prevent re-triggering of the same event, since we already bubbled it above
                        jQuery.event.triggered = type;
    
                        if ( event.isPropagationStopped() ) {
                            lastElement.addEventListener( type, stopPropagationCallback );
                        }
    
                        elem[ type ]();
    
                        if ( event.isPropagationStopped() ) {
                            lastElement.removeEventListener( type, stopPropagationCallback );
                        }
    
                        jQuery.event.triggered = undefined;
    
                        if ( tmp ) {
                            elem[ ontype ] = tmp;
                        }
                    }
                }
            }
    
            return event.result;
        },
    
        // Piggyback on a donor event to simulate a different one
        // Used only for `focus(in | out)` events
        simulate: function( type, elem, event ) {
            var e = jQuery.extend(
                new jQuery.Event(),
                event,
                {
                    type: type,
                    isSimulated: true
                }
            );
    
            jQuery.event.trigger( e, null, elem );
        }
    
    } );
    
    jQuery.fn.extend( {
    
        trigger: function( type, data ) {
            return this.each( function() {
                jQuery.event.trigger( type, data, this );
            } );
        },
        triggerHandler: function( type, data ) {
            var elem = this[ 0 ];
            if ( elem ) {
                return jQuery.event.trigger( type, data, elem, true );
            }
        }
    } );
    
    
    // Support: Firefox <=44
    // Firefox doesn't have focus(in | out) events
    // Related ticket - https://bugzilla.mozilla.org/show_bug.cgi?id=687787
    //
    // Support: Chrome <=48 - 49, Safari <=9.0 - 9.1
    // focus(in | out) events fire after focus & blur events,
    // which is spec violation - http://www.w3.org/TR/DOM-Level-3-Events/#events-focusevent-event-order
    // Related ticket - https://bugs.chromium.org/p/chromium/issues/detail?id=449857
    if ( !support.focusin ) {
        jQuery.each( { focus: "focusin", blur: "focusout" }, function( orig, fix ) {
    
            // Attach a single capturing handler on the document while someone wants focusin/focusout
            var handler = function( event ) {
                jQuery.event.simulate( fix, event.target, jQuery.event.fix( event ) );
            };
    
            jQuery.event.special[ fix ] = {
                setup: function() {
    
                    // Handle: regular nodes (via `this.ownerDocument`), window
                    // (via `this.document`) & document (via `this`).
                    var doc = this.ownerDocument || this.document || this,
                        attaches = dataPriv.access( doc, fix );
    
                    if ( !attaches ) {
                        doc.addEventListener( orig, handler, true );
                    }
                    dataPriv.access( doc, fix, ( attaches || 0 ) + 1 );
                },
                teardown: function() {
                    var doc = this.ownerDocument || this.document || this,
                        attaches = dataPriv.access( doc, fix ) - 1;
    
                    if ( !attaches ) {
                        doc.removeEventListener( orig, handler, true );
                        dataPriv.remove( doc, fix );
    
                    } else {
                        dataPriv.access( doc, fix, attaches );
                    }
                }
            };
        } );
    }
    var location = window.location;
    
    var nonce = { guid: Date.now() };
    
    var rquery = ( /\?/ );
    
    
    
    // Cross-browser xml parsing
    jQuery.parseXML = function( data ) {
        var xml, parserErrorElem;
        if ( !data || typeof data !== "string" ) {
            return null;
        }
    
        // Support: IE 9 - 11 only
        // IE throws on parseFromString with invalid input.
        try {
            xml = ( new window.DOMParser() ).parseFromString( data, "text/xml" );
        } catch ( e ) {}
    
        parserErrorElem = xml && xml.getElementsByTagName( "parsererror" )[ 0 ];
        if ( !xml || parserErrorElem ) {
            jQuery.error( "Invalid XML: " + (
                parserErrorElem ?
                    jQuery.map( parserErrorElem.childNodes, function( el ) {
                        return el.textContent;
                    } ).join( "\n" ) :
                    data
            ) );
        }
        return xml;
    };
    
    
    var
        rbracket = /\[\]$/,
        rCRLF = /\r?\n/g,
        rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
        rsubmittable = /^(?:input|select|textarea|keygen)/i;
    
    function buildParams( prefix, obj, traditional, add ) {
        var name;
    
        if ( Array.isArray( obj ) ) {
    
            // Serialize array item.
            jQuery.each( obj, function( i, v ) {
                if ( traditional || rbracket.test( prefix ) ) {
    
                    // Treat each array item as a scalar.
                    add( prefix, v );
    
                } else {
    
                    // Item is non-scalar (array or object), encode its numeric index.
                    buildParams(
                        prefix + "[" + ( typeof v === "object" && v != null ? i : "" ) + "]",
                        v,
                        traditional,
                        add
                    );
                }
            } );
    
        } else if ( !traditional && toType( obj ) === "object" ) {
    
            // Serialize object item.
            for ( name in obj ) {
                buildParams( prefix + "[" + name + "]", obj[ name ], traditional, add );
            }
    
        } else {
    
            // Serialize scalar item.
            add( prefix, obj );
        }
    }
    
    // Serialize an array of form elements or a set of
    // key/values into a query string
    jQuery.param = function( a, traditional ) {
        var prefix,
            s = [],
            add = function( key, valueOrFunction ) {
    
                // If value is a function, invoke it and use its return value
                var value = isFunction( valueOrFunction ) ?
                    valueOrFunction() :
                    valueOrFunction;
    
                s[ s.length ] = encodeURIComponent( key ) + "=" +
                    encodeURIComponent( value == null ? "" : value );
            };
    
        if ( a == null ) {
            return "";
        }
    
        // If an array was passed in, assume that it is an array of form elements.
        if ( Array.isArray( a ) || ( a.jquery && !jQuery.isPlainObject( a ) ) ) {
    
            // Serialize the form elements
            jQuery.each( a, function() {
                add( this.name, this.value );
            } );
    
        } else {
    
            // If traditional, encode the "old" way (the way 1.3.2 or older
            // did it), otherwise encode params recursively.
            for ( prefix in a ) {
                buildParams( prefix, a[ prefix ], traditional, add );
            }
        }
    
        // Return the resulting serialization
        return s.join( "&" );
    };
    
    jQuery.fn.extend( {
        serialize: function() {
            return jQuery.param( this.serializeArray() );
        },
        serializeArray: function() {
            return this.map( function() {
    
                // Can add propHook for "elements" to filter or add form elements
                var elements = jQuery.prop( this, "elements" );
                return elements ? jQuery.makeArray( elements ) : this;
            } ).filter( function() {
                var type = this.type;
    
                // Use .is( ":disabled" ) so that fieldset[disabled] works
                return this.name && !jQuery( this ).is( ":disabled" ) &&
                    rsubmittable.test( this.nodeName ) && !rsubmitterTypes.test( type ) &&
                    ( this.checked || !rcheckableType.test( type ) );
            } ).map( function( _i, elem ) {
                var val = jQuery( this ).val();
    
                if ( val == null ) {
                    return null;
                }
    
                if ( Array.isArray( val ) ) {
                    return jQuery.map( val, function( val ) {
                        return { name: elem.name, value: val.replace( rCRLF, "\r\n" ) };
                    } );
                }
    
                return { name: elem.name, value: val.replace( rCRLF, "\r\n" ) };
            } ).get();
        }
    } );
    
    
    var
        r20 = /%20/g,
        rhash = /#.*$/,
        rantiCache = /([?&])_=[^&]*/,
        rheaders = /^(.*?):[ \t]*([^\r\n]*)$/mg,
    
        // #7653, #8125, #8152: local protocol detection
        rlocalProtocol = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/,
        rnoContent = /^(?:GET|HEAD)$/,
        rprotocol = /^\/\//,
    
        /* Prefilters
         * 1) They are useful to introduce custom dataTypes (see ajax/jsonp.js for an example)
         * 2) These are called:
         *    - BEFORE asking for a transport
         *    - AFTER param serialization (s.data is a string if s.processData is true)
         * 3) key is the dataType
         * 4) the catchall symbol "*" can be used
         * 5) execution will start with transport dataType and THEN continue down to "*" if needed
         */
        prefilters = {},
    
        /* Transports bindings
         * 1) key is the dataType
         * 2) the catchall symbol "*" can be used
         * 3) selection will start with transport dataType and THEN go to "*" if needed
         */
        transports = {},
    
        // Avoid comment-prolog char sequence (#10098); must appease lint and evade compression
        allTypes = "*/".concat( "*" ),
    
        // Anchor tag for parsing the document origin
        originAnchor = document.createElement( "a" );
    
    originAnchor.href = location.href;
    
    // Base "constructor" for jQuery.ajaxPrefilter and jQuery.ajaxTransport
    function addToPrefiltersOrTransports( structure ) {
    
        // dataTypeExpression is optional and defaults to "*"
        return function( dataTypeExpression, func ) {
    
            if ( typeof dataTypeExpression !== "string" ) {
                func = dataTypeExpression;
                dataTypeExpression = "*";
            }
    
            var dataType,
                i = 0,
                dataTypes = dataTypeExpression.toLowerCase().match( rnothtmlwhite ) || [];
    
            if ( isFunction( func ) ) {
    
                // For each dataType in the dataTypeExpression
                while ( ( dataType = dataTypes[ i++ ] ) ) {
    
                    // Prepend if requested
                    if ( dataType[ 0 ] === "+" ) {
                        dataType = dataType.slice( 1 ) || "*";
                        ( structure[ dataType ] = structure[ dataType ] || [] ).unshift( func );
    
                    // Otherwise append
                    } else {
                        ( structure[ dataType ] = structure[ dataType ] || [] ).push( func );
                    }
                }
            }
        };
    }
    
    // Base inspection function for prefilters and transports
    function inspectPrefiltersOrTransports( structure, options, originalOptions, jqXHR ) {
    
        var inspected = {},
            seekingTransport = ( structure === transports );
    
        function inspect( dataType ) {
            var selected;
            inspected[ dataType ] = true;
            jQuery.each( structure[ dataType ] || [], function( _, prefilterOrFactory ) {
                var dataTypeOrTransport = prefilterOrFactory( options, originalOptions, jqXHR );
                if ( typeof dataTypeOrTransport === "string" &&
                    !seekingTransport && !inspected[ dataTypeOrTransport ] ) {
    
                    options.dataTypes.unshift( dataTypeOrTransport );
                    inspect( dataTypeOrTransport );
                    return false;
                } else if ( seekingTransport ) {
                    return !( selected = dataTypeOrTransport );
                }
            } );
            return selected;
        }
    
        return inspect( options.dataTypes[ 0 ] ) || !inspected[ "*" ] && inspect( "*" );
    }
    
    // A special extend for ajax options
    // that takes "flat" options (not to be deep extended)
    // Fixes #9887
    function ajaxExtend( target, src ) {
        var key, deep,
            flatOptions = jQuery.ajaxSettings.flatOptions || {};
    
        for ( key in src ) {
            if ( src[ key ] !== undefined ) {
                ( flatOptions[ key ] ? target : ( deep || ( deep = {} ) ) )[ key ] = src[ key ];
            }
        }
        if ( deep ) {
            jQuery.extend( true, target, deep );
        }
    
        return target;
    }
    
    /* Handles responses to an ajax request:
     * - finds the right dataType (mediates between content-type and expected dataType)
     * - returns the corresponding response
     */
    function ajaxHandleResponses( s, jqXHR, responses ) {
    
        var ct, type, finalDataType, firstDataType,
            contents = s.contents,
            dataTypes = s.dataTypes;
    
        // Remove auto dataType and get content-type in the process
        while ( dataTypes[ 0 ] === "*" ) {
            dataTypes.shift();
            if ( ct === undefined ) {
                ct = s.mimeType || jqXHR.getResponseHeader( "Content-Type" );
            }
        }
    
        // Check if we're dealing with a known content-type
        if ( ct ) {
            for ( type in contents ) {
                if ( contents[ type ] && contents[ type ].test( ct ) ) {
                    dataTypes.unshift( type );
                    break;
                }
            }
        }
    
        // Check to see if we have a response for the expected dataType
        if ( dataTypes[ 0 ] in responses ) {
            finalDataType = dataTypes[ 0 ];
        } else {
    
            // Try convertible dataTypes
            for ( type in responses ) {
                if ( !dataTypes[ 0 ] || s.converters[ type + " " + dataTypes[ 0 ] ] ) {
                    finalDataType = type;
                    break;
                }
                if ( !firstDataType ) {
                    firstDataType = type;
                }
            }
    
            // Or just use first one
            finalDataType = finalDataType || firstDataType;
        }
    
        // If we found a dataType
        // We add the dataType to the list if needed
        // and return the corresponding response
        if ( finalDataType ) {
            if ( finalDataType !== dataTypes[ 0 ] ) {
                dataTypes.unshift( finalDataType );
            }
            return responses[ finalDataType ];
        }
    }
    
    /* Chain conversions given the request and the original response
     * Also sets the responseXXX fields on the jqXHR instance
     */
    function ajaxConvert( s, response, jqXHR, isSuccess ) {
        var conv2, current, conv, tmp, prev,
            converters = {},
    
            // Work with a copy of dataTypes in case we need to modify it for conversion
            dataTypes = s.dataTypes.slice();
    
        // Create converters map with lowercased keys
        if ( dataTypes[ 1 ] ) {
            for ( conv in s.converters ) {
                converters[ conv.toLowerCase() ] = s.converters[ conv ];
            }
        }
    
        current = dataTypes.shift();
    
        // Convert to each sequential dataType
        while ( current ) {
    
            if ( s.responseFields[ current ] ) {
                jqXHR[ s.responseFields[ current ] ] = response;
            }
    
            // Apply the dataFilter if provided
            if ( !prev && isSuccess && s.dataFilter ) {
                response = s.dataFilter( response, s.dataType );
            }
    
            prev = current;
            current = dataTypes.shift();
    
            if ( current ) {
    
                // There's only work to do if current dataType is non-auto
                if ( current === "*" ) {
    
                    current = prev;
    
                // Convert response if prev dataType is non-auto and differs from current
                } else if ( prev !== "*" && prev !== current ) {
    
                    // Seek a direct converter
                    conv = converters[ prev + " " + current ] || converters[ "* " + current ];
    
                    // If none found, seek a pair
                    if ( !conv ) {
                        for ( conv2 in converters ) {
    
                            // If conv2 outputs current
                            tmp = conv2.split( " " );
                            if ( tmp[ 1 ] === current ) {
    
                                // If prev can be converted to accepted input
                                conv = converters[ prev + " " + tmp[ 0 ] ] ||
                                    converters[ "* " + tmp[ 0 ] ];
                                if ( conv ) {
    
                                    // Condense equivalence converters
                                    if ( conv === true ) {
                                        conv = converters[ conv2 ];
    
                                    // Otherwise, insert the intermediate dataType
                                    } else if ( converters[ conv2 ] !== true ) {
                                        current = tmp[ 0 ];
                                        dataTypes.unshift( tmp[ 1 ] );
                                    }
                                    break;
                                }
                            }
                        }
                    }
    
                    // Apply converter (if not an equivalence)
                    if ( conv !== true ) {
    
                        // Unless errors are allowed to bubble, catch and return them
                        if ( conv && s.throws ) {
                            response = conv( response );
                        } else {
                            try {
                                response = conv( response );
                            } catch ( e ) {
                                return {
                                    state: "parsererror",
                                    error: conv ? e : "No conversion from " + prev + " to " + current
                                };
                            }
                        }
                    }
                }
            }
        }
    
        return { state: "success", data: response };
    }
    
    jQuery.extend( {
    
        // Counter for holding the number of active queries
        active: 0,
    
        // Last-Modified header cache for next request
        lastModified: {},
        etag: {},
    
        ajaxSettings: {
            url: location.href,
            type: "GET",
            isLocal: rlocalProtocol.test( location.protocol ),
            global: true,
            processData: true,
            async: true,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
    
            /*
            timeout: 0,
            data: null,
            dataType: null,
            username: null,
            password: null,
            cache: null,
            throws: false,
            traditional: false,
            headers: {},
            */
    
            accepts: {
                "*": allTypes,
                text: "text/plain",
                html: "text/html",
                xml: "application/xml, text/xml",
                json: "application/json, text/javascript"
            },
    
            contents: {
                xml: /\bxml\b/,
                html: /\bhtml/,
                json: /\bjson\b/
            },
    
            responseFields: {
                xml: "responseXML",
                text: "responseText",
                json: "responseJSON"
            },
    
            // Data converters
            // Keys separate source (or catchall "*") and destination types with a single space
            converters: {
    
                // Convert anything to text
                "* text": String,
    
                // Text to html (true = no transformation)
                "text html": true,
    
                // Evaluate text as a json expression
                "text json": JSON.parse,
    
                // Parse text as xml
                "text xml": jQuery.parseXML
            },
    
            // For options that shouldn't be deep extended:
            // you can add your own custom options here if
            // and when you create one that shouldn't be
            // deep extended (see ajaxExtend)
            flatOptions: {
                url: true,
                context: true
            }
        },
    
        // Creates a full fledged settings object into target
        // with both ajaxSettings and settings fields.
        // If target is omitted, writes into ajaxSettings.
        ajaxSetup: function( target, settings ) {
            return settings ?
    
                // Building a settings object
                ajaxExtend( ajaxExtend( target, jQuery.ajaxSettings ), settings ) :
    
                // Extending ajaxSettings
                ajaxExtend( jQuery.ajaxSettings, target );
        },
    
        ajaxPrefilter: addToPrefiltersOrTransports( prefilters ),
        ajaxTransport: addToPrefiltersOrTransports( transports ),
    
        // Main method
        ajax: function( url, options ) {
    
            // If url is an object, simulate pre-1.5 signature
            if ( typeof url === "object" ) {
                options = url;
                url = undefined;
            }
    
            // Force options to be an object
            options = options || {};
    
            var transport,
    
                // URL without anti-cache param
                cacheURL,
    
                // Response headers
                responseHeadersString,
                responseHeaders,
    
                // timeout handle
                timeoutTimer,
    
                // Url cleanup var
                urlAnchor,
    
                // Request state (becomes false upon send and true upon completion)
                completed,
    
                // To know if global events are to be dispatched
                fireGlobals,
    
                // Loop variable
                i,
    
                // uncached part of the url
                uncached,
    
                // Create the final options object
                s = jQuery.ajaxSetup( {}, options ),
    
                // Callbacks context
                callbackContext = s.context || s,
    
                // Context for global events is callbackContext if it is a DOM node or jQuery collection
                globalEventContext = s.context &&
                    ( callbackContext.nodeType || callbackContext.jquery ) ?
                    jQuery( callbackContext ) :
                    jQuery.event,
    
                // Deferreds
                deferred = jQuery.Deferred(),
                completeDeferred = jQuery.Callbacks( "once memory" ),
    
                // Status-dependent callbacks
                statusCode = s.statusCode || {},
    
                // Headers (they are sent all at once)
                requestHeaders = {},
                requestHeadersNames = {},
    
                // Default abort message
                strAbort = "canceled",
    
                // Fake xhr
                jqXHR = {
                    readyState: 0,
    
                    // Builds headers hashtable if needed
                    getResponseHeader: function( key ) {
                        var match;
                        if ( completed ) {
                            if ( !responseHeaders ) {
                                responseHeaders = {};
                                while ( ( match = rheaders.exec( responseHeadersString ) ) ) {
                                    responseHeaders[ match[ 1 ].toLowerCase() + " " ] =
                                        ( responseHeaders[ match[ 1 ].toLowerCase() + " " ] || [] )
                                            .concat( match[ 2 ] );
                                }
                            }
                            match = responseHeaders[ key.toLowerCase() + " " ];
                        }
                        return match == null ? null : match.join( ", " );
                    },
    
                    // Raw string
                    getAllResponseHeaders: function() {
                        return completed ? responseHeadersString : null;
                    },
    
                    // Caches the header
                    setRequestHeader: function( name, value ) {
                        if ( completed == null ) {
                            name = requestHeadersNames[ name.toLowerCase() ] =
                                requestHeadersNames[ name.toLowerCase() ] || name;
                            requestHeaders[ name ] = value;
                        }
                        return this;
                    },
    
                    // Overrides response content-type header
                    overrideMimeType: function( type ) {
                        if ( completed == null ) {
                            s.mimeType = type;
                        }
                        return this;
                    },
    
                    // Status-dependent callbacks
                    statusCode: function( map ) {
                        var code;
                        if ( map ) {
                            if ( completed ) {
    
                                // Execute the appropriate callbacks
                                jqXHR.always( map[ jqXHR.status ] );
                            } else {
    
                                // Lazy-add the new callbacks in a way that preserves old ones
                                for ( code in map ) {
                                    statusCode[ code ] = [ statusCode[ code ], map[ code ] ];
                                }
                            }
                        }
                        return this;
                    },
    
                    // Cancel the request
                    abort: function( statusText ) {
                        var finalText = statusText || strAbort;
                        if ( transport ) {
                            transport.abort( finalText );
                        }
                        done( 0, finalText );
                        return this;
                    }
                };
    
            // Attach deferreds
            deferred.promise( jqXHR );
    
            // Add protocol if not provided (prefilters might expect it)
            // Handle falsy url in the settings object (#10093: consistency with old signature)
            // We also use the url parameter if available
            s.url = ( ( url || s.url || location.href ) + "" )
                .replace( rprotocol, location.protocol + "//" );
    
            // Alias method option to type as per ticket #12004
            s.type = options.method || options.type || s.method || s.type;
    
            // Extract dataTypes list
            s.dataTypes = ( s.dataType || "*" ).toLowerCase().match( rnothtmlwhite ) || [ "" ];
    
            // A cross-domain request is in order when the origin doesn't match the current origin.
            if ( s.crossDomain == null ) {
                urlAnchor = document.createElement( "a" );
    
                // Support: IE <=8 - 11, Edge 12 - 15
                // IE throws exception on accessing the href property if url is malformed,
                // e.g. http://example.com:80x/
                try {
                    urlAnchor.href = s.url;
    
                    // Support: IE <=8 - 11 only
                    // Anchor's host property isn't correctly set when s.url is relative
                    urlAnchor.href = urlAnchor.href;
                    s.crossDomain = originAnchor.protocol + "//" + originAnchor.host !==
                        urlAnchor.protocol + "//" + urlAnchor.host;
                } catch ( e ) {
    
                    // If there is an error parsing the URL, assume it is crossDomain,
                    // it can be rejected by the transport if it is invalid
                    s.crossDomain = true;
                }
            }
    
            // Convert data if not already a string
            if ( s.data && s.processData && typeof s.data !== "string" ) {
                s.data = jQuery.param( s.data, s.traditional );
            }
    
            // Apply prefilters
            inspectPrefiltersOrTransports( prefilters, s, options, jqXHR );
    
            // If request was aborted inside a prefilter, stop there
            if ( completed ) {
                return jqXHR;
            }
    
            // We can fire global events as of now if asked to
            // Don't fire events if jQuery.event is undefined in an AMD-usage scenario (#15118)
            fireGlobals = jQuery.event && s.global;
    
            // Watch for a new set of requests
            if ( fireGlobals && jQuery.active++ === 0 ) {
                jQuery.event.trigger( "ajaxStart" );
            }
    
            // Uppercase the type
            s.type = s.type.toUpperCase();
    
            // Determine if request has content
            s.hasContent = !rnoContent.test( s.type );
    
            // Save the URL in case we're toying with the If-Modified-Since
            // and/or If-None-Match header later on
            // Remove hash to simplify url manipulation
            cacheURL = s.url.replace( rhash, "" );
    
            // More options handling for requests with no content
            if ( !s.hasContent ) {
    
                // Remember the hash so we can put it back
                uncached = s.url.slice( cacheURL.length );
    
                // If data is available and should be processed, append data to url
                if ( s.data && ( s.processData || typeof s.data === "string" ) ) {
                    cacheURL += ( rquery.test( cacheURL ) ? "&" : "?" ) + s.data;
    
                    // #9682: remove data so that it's not used in an eventual retry
                    delete s.data;
                }
    
                // Add or update anti-cache param if needed
                if ( s.cache === false ) {
                    cacheURL = cacheURL.replace( rantiCache, "$1" );
                    uncached = ( rquery.test( cacheURL ) ? "&" : "?" ) + "_=" + ( nonce.guid++ ) +
                        uncached;
                }
    
                // Put hash and anti-cache on the URL that will be requested (gh-1732)
                s.url = cacheURL + uncached;
    
            // Change '%20' to '+' if this is encoded form body content (gh-2658)
            } else if ( s.data && s.processData &&
                ( s.contentType || "" ).indexOf( "application/x-www-form-urlencoded" ) === 0 ) {
                s.data = s.data.replace( r20, "+" );
            }
    
            // Set the If-Modified-Since and/or If-None-Match header, if in ifModified mode.
            if ( s.ifModified ) {
                if ( jQuery.lastModified[ cacheURL ] ) {
                    jqXHR.setRequestHeader( "If-Modified-Since", jQuery.lastModified[ cacheURL ] );
                }
                if ( jQuery.etag[ cacheURL ] ) {
                    jqXHR.setRequestHeader( "If-None-Match", jQuery.etag[ cacheURL ] );
                }
            }
    
            // Set the correct header, if data is being sent
            if ( s.data && s.hasContent && s.contentType !== false || options.contentType ) {
                jqXHR.setRequestHeader( "Content-Type", s.contentType );
            }
    
            // Set the Accepts header for the server, depending on the dataType
            jqXHR.setRequestHeader(
                "Accept",
                s.dataTypes[ 0 ] && s.accepts[ s.dataTypes[ 0 ] ] ?
                    s.accepts[ s.dataTypes[ 0 ] ] +
                        ( s.dataTypes[ 0 ] !== "*" ? ", " + allTypes + "; q=0.01" : "" ) :
                    s.accepts[ "*" ]
            );
    
            // Check for headers option
            for ( i in s.headers ) {
                jqXHR.setRequestHeader( i, s.headers[ i ] );
            }
    
            // Allow custom headers/mimetypes and early abort
            if ( s.beforeSend &&
                ( s.beforeSend.call( callbackContext, jqXHR, s ) === false || completed ) ) {
    
                // Abort if not done already and return
                return jqXHR.abort();
            }
    
            // Aborting is no longer a cancellation
            strAbort = "abort";
    
            // Install callbacks on deferreds
            completeDeferred.add( s.complete );
            jqXHR.done( s.success );
            jqXHR.fail( s.error );
    
            // Get transport
            transport = inspectPrefiltersOrTransports( transports, s, options, jqXHR );
    
            // If no transport, we auto-abort
            if ( !transport ) {
                done( -1, "No Transport" );
            } else {
                jqXHR.readyState = 1;
    
                // Send global event
                if ( fireGlobals ) {
                    globalEventContext.trigger( "ajaxSend", [ jqXHR, s ] );
                }
    
                // If request was aborted inside ajaxSend, stop there
                if ( completed ) {
                    return jqXHR;
                }
    
                // Timeout
                if ( s.async && s.timeout > 0 ) {
                    timeoutTimer = window.setTimeout( function() {
                        jqXHR.abort( "timeout" );
                    }, s.timeout );
                }
    
                try {
                    completed = false;
                    transport.send( requestHeaders, done );
                } catch ( e ) {
    
                    // Rethrow post-completion exceptions
                    if ( completed ) {
                        throw e;
                    }
    
                    // Propagate others as results
                    done( -1, e );
                }
            }
    
            // Callback for when everything is done
            function done( status, nativeStatusText, responses, headers ) {
                var isSuccess, success, error, response, modified,
                    statusText = nativeStatusText;
    
                // Ignore repeat invocations
                if ( completed ) {
                    return;
                }
    
                completed = true;
    
                // Clear timeout if it exists
                if ( timeoutTimer ) {
                    window.clearTimeout( timeoutTimer );
                }
    
                // Dereference transport for early garbage collection
                // (no matter how long the jqXHR object will be used)
                transport = undefined;
    
                // Cache response headers
                responseHeadersString = headers || "";
    
                // Set readyState
                jqXHR.readyState = status > 0 ? 4 : 0;
    
                // Determine if successful
                isSuccess = status >= 200 && status < 300 || status === 304;
    
                // Get response data
                if ( responses ) {
                    response = ajaxHandleResponses( s, jqXHR, responses );
                }
    
                // Use a noop converter for missing script but not if jsonp
                if ( !isSuccess &&
                    jQuery.inArray( "script", s.dataTypes ) > -1 &&
                    jQuery.inArray( "json", s.dataTypes ) < 0 ) {
                    s.converters[ "text script" ] = function() {};
                }
    
                // Convert no matter what (that way responseXXX fields are always set)
                response = ajaxConvert( s, response, jqXHR, isSuccess );
    
                // If successful, handle type chaining
                if ( isSuccess ) {
    
                    // Set the If-Modified-Since and/or If-None-Match header, if in ifModified mode.
                    if ( s.ifModified ) {
                        modified = jqXHR.getResponseHeader( "Last-Modified" );
                        if ( modified ) {
                            jQuery.lastModified[ cacheURL ] = modified;
                        }
                        modified = jqXHR.getResponseHeader( "etag" );
                        if ( modified ) {
                            jQuery.etag[ cacheURL ] = modified;
                        }
                    }
    
                    // if no content
                    if ( status === 204 || s.type === "HEAD" ) {
                        statusText = "nocontent";
    
                    // if not modified
                    } else if ( status === 304 ) {
                        statusText = "notmodified";
    
                    // If we have data, let's convert it
                    } else {
                        statusText = response.state;
                        success = response.data;
                        error = response.error;
                        isSuccess = !error;
                    }
                } else {
    
                    // Extract error from statusText and normalize for non-aborts
                    error = statusText;
                    if ( status || !statusText ) {
                        statusText = "error";
                        if ( status < 0 ) {
                            status = 0;
                        }
                    }
                }
    
                // Set data for the fake xhr object
                jqXHR.status = status;
                jqXHR.statusText = ( nativeStatusText || statusText ) + "";
    
                // Success/Error
                if ( isSuccess ) {
                    deferred.resolveWith( callbackContext, [ success, statusText, jqXHR ] );
                } else {
                    deferred.rejectWith( callbackContext, [ jqXHR, statusText, error ] );
                }
    
                // Status-dependent callbacks
                jqXHR.statusCode( statusCode );
                statusCode = undefined;
    
                if ( fireGlobals ) {
                    globalEventContext.trigger( isSuccess ? "ajaxSuccess" : "ajaxError",
                        [ jqXHR, s, isSuccess ? success : error ] );
                }
    
                // Complete
                completeDeferred.fireWith( callbackContext, [ jqXHR, statusText ] );
    
                if ( fireGlobals ) {
                    globalEventContext.trigger( "ajaxComplete", [ jqXHR, s ] );
    
                    // Handle the global AJAX counter
                    if ( !( --jQuery.active ) ) {
                        jQuery.event.trigger( "ajaxStop" );
                    }
                }
            }
    
            return jqXHR;
        },
    
        getJSON: function( url, data, callback ) {
            return jQuery.get( url, data, callback, "json" );
        },
    
        getScript: function( url, callback ) {
            return jQuery.get( url, undefined, callback, "script" );
        }
    } );
    
    jQuery.each( [ "get", "post" ], function( _i, method ) {
        jQuery[ method ] = function( url, data, callback, type ) {
    
            // Shift arguments if data argument was omitted
            if ( isFunction( data ) ) {
                type = type || callback;
                callback = data;
                data = undefined;
            }
    
            // The url can be an options object (which then must have .url)
            return jQuery.ajax( jQuery.extend( {
                url: url,
                type: method,
                dataType: type,
                data: data,
                success: callback
            }, jQuery.isPlainObject( url ) && url ) );
        };
    } );
    
    jQuery.ajaxPrefilter( function( s ) {
        var i;
        for ( i in s.headers ) {
            if ( i.toLowerCase() === "content-type" ) {
                s.contentType = s.headers[ i ] || "";
            }
        }
    } );
    
    
    jQuery._evalUrl = function( url, options, doc ) {
        return jQuery.ajax( {
            url: url,
    
            // Make this explicit, since user can override this through ajaxSetup (#11264)
            type: "GET",
            dataType: "script",
            cache: true,
            async: false,
            global: false,
    
            // Only evaluate the response if it is successful (gh-4126)
            // dataFilter is not invoked for failure responses, so using it instead
            // of the default converter is kludgy but it works.
            converters: {
                "text script": function() {}
            },
            dataFilter: function( response ) {
                jQuery.globalEval( response, options, doc );
            }
        } );
    };
    
    
    jQuery.fn.extend( {
        wrapAll: function( html ) {
            var wrap;
    
            if ( this[ 0 ] ) {
                if ( isFunction( html ) ) {
                    html = html.call( this[ 0 ] );
                }
    
                // The elements to wrap the target around
                wrap = jQuery( html, this[ 0 ].ownerDocument ).eq( 0 ).clone( true );
    
                if ( this[ 0 ].parentNode ) {
                    wrap.insertBefore( this[ 0 ] );
                }
    
                wrap.map( function() {
                    var elem = this;
    
                    while ( elem.firstElementChild ) {
                        elem = elem.firstElementChild;
                    }
    
                    return elem;
                } ).append( this );
            }
    
            return this;
        },
    
        wrapInner: function( html ) {
            if ( isFunction( html ) ) {
                return this.each( function( i ) {
                    jQuery( this ).wrapInner( html.call( this, i ) );
                } );
            }
    
            return this.each( function() {
                var self = jQuery( this ),
                    contents = self.contents();
    
                if ( contents.length ) {
                    contents.wrapAll( html );
    
                } else {
                    self.append( html );
                }
            } );
        },
    
        wrap: function( html ) {
            var htmlIsFunction = isFunction( html );
    
            return this.each( function( i ) {
                jQuery( this ).wrapAll( htmlIsFunction ? html.call( this, i ) : html );
            } );
        },
    
        unwrap: function( selector ) {
            this.parent( selector ).not( "body" ).each( function() {
                jQuery( this ).replaceWith( this.childNodes );
            } );
            return this;
        }
    } );
    
    
    jQuery.expr.pseudos.hidden = function( elem ) {
        return !jQuery.expr.pseudos.visible( elem );
    };
    jQuery.expr.pseudos.visible = function( elem ) {
        return !!( elem.offsetWidth || elem.offsetHeight || elem.getClientRects().length );
    };
    
    
    
    
    jQuery.ajaxSettings.xhr = function() {
        try {
            return new window.XMLHttpRequest();
        } catch ( e ) {}
    };
    
    var xhrSuccessStatus = {
    
            // File protocol always yields status code 0, assume 200
            0: 200,
    
            // Support: IE <=9 only
            // #1450: sometimes IE returns 1223 when it should be 204
            1223: 204
        },
        xhrSupported = jQuery.ajaxSettings.xhr();
    
    support.cors = !!xhrSupported && ( "withCredentials" in xhrSupported );
    support.ajax = xhrSupported = !!xhrSupported;
    
    jQuery.ajaxTransport( function( options ) {
        var callback, errorCallback;
    
        // Cross domain only allowed if supported through XMLHttpRequest
        if ( support.cors || xhrSupported && !options.crossDomain ) {
            return {
                send: function( headers, complete ) {
                    var i,
                        xhr = options.xhr();
    
                    xhr.open(
                        options.type,
                        options.url,
                        options.async,
                        options.username,
                        options.password
                    );
    
                    // Apply custom fields if provided
                    if ( options.xhrFields ) {
                        for ( i in options.xhrFields ) {
                            xhr[ i ] = options.xhrFields[ i ];
                        }
                    }
    
                    // Override mime type if needed
                    if ( options.mimeType && xhr.overrideMimeType ) {
                        xhr.overrideMimeType( options.mimeType );
                    }
    
                    // X-Requested-With header
                    // For cross-domain requests, seeing as conditions for a preflight are
                    // akin to a jigsaw puzzle, we simply never set it to be sure.
                    // (it can always be set on a per-request basis or even using ajaxSetup)
                    // For same-domain requests, won't change header if already provided.
                    if ( !options.crossDomain && !headers[ "X-Requested-With" ] ) {
                        headers[ "X-Requested-With" ] = "XMLHttpRequest";
                    }
    
                    // Set headers
                    for ( i in headers ) {
                        xhr.setRequestHeader( i, headers[ i ] );
                    }
    
                    // Callback
                    callback = function( type ) {
                        return function() {
                            if ( callback ) {
                                callback = errorCallback = xhr.onload =
                                    xhr.onerror = xhr.onabort = xhr.ontimeout =
                                        xhr.onreadystatechange = null;
    
                                if ( type === "abort" ) {
                                    xhr.abort();
                                } else if ( type === "error" ) {
    
                                    // Support: IE <=9 only
                                    // On a manual native abort, IE9 throws
                                    // errors on any property access that is not readyState
                                    if ( typeof xhr.status !== "number" ) {
                                        complete( 0, "error" );
                                    } else {
                                        complete(
    
                                            // File: protocol always yields status 0; see #8605, #14207
                                            xhr.status,
                                            xhr.statusText
                                        );
                                    }
                                } else {
                                    complete(
                                        xhrSuccessStatus[ xhr.status ] || xhr.status,
                                        xhr.statusText,
    
                                        // Support: IE <=9 only
                                        // IE9 has no XHR2 but throws on binary (trac-11426)
                                        // For XHR2 non-text, let the caller handle it (gh-2498)
                                        ( xhr.responseType || "text" ) !== "text"  ||
                                        typeof xhr.responseText !== "string" ?
                                            { binary: xhr.response } :
                                            { text: xhr.responseText },
                                        xhr.getAllResponseHeaders()
                                    );
                                }
                            }
                        };
                    };
    
                    // Listen to events
                    xhr.onload = callback();
                    errorCallback = xhr.onerror = xhr.ontimeout = callback( "error" );
    
                    // Support: IE 9 only
                    // Use onreadystatechange to replace onabort
                    // to handle uncaught aborts
                    if ( xhr.onabort !== undefined ) {
                        xhr.onabort = errorCallback;
                    } else {
                        xhr.onreadystatechange = function() {
    
                            // Check readyState before timeout as it changes
                            if ( xhr.readyState === 4 ) {
    
                                // Allow onerror to be called first,
                                // but that will not handle a native abort
                                // Also, save errorCallback to a variable
                                // as xhr.onerror cannot be accessed
                                window.setTimeout( function() {
                                    if ( callback ) {
                                        errorCallback();
                                    }
                                } );
                            }
                        };
                    }
    
                    // Create the abort callback
                    callback = callback( "abort" );
    
                    try {
    
                        // Do send the request (this may raise an exception)
                        xhr.send( options.hasContent && options.data || null );
                    } catch ( e ) {
    
                        // #14683: Only rethrow if this hasn't been notified as an error yet
                        if ( callback ) {
                            throw e;
                        }
                    }
                },
    
                abort: function() {
                    if ( callback ) {
                        callback();
                    }
                }
            };
        }
    } );
    
    
    
    
    // Prevent auto-execution of scripts when no explicit dataType was provided (See gh-2432)
    jQuery.ajaxPrefilter( function( s ) {
        if ( s.crossDomain ) {
            s.contents.script = false;
        }
    } );
    
    // Install script dataType
    jQuery.ajaxSetup( {
        accepts: {
            script: "text/javascript, application/javascript, " +
                "application/ecmascript, application/x-ecmascript"
        },
        contents: {
            script: /\b(?:java|ecma)script\b/
        },
        converters: {
            "text script": function( text ) {
                jQuery.globalEval( text );
                return text;
            }
        }
    } );
    
    // Handle cache's special case and crossDomain
    jQuery.ajaxPrefilter( "script", function( s ) {
        if ( s.cache === undefined ) {
            s.cache = false;
        }
        if ( s.crossDomain ) {
            s.type = "GET";
        }
    } );
    
    // Bind script tag hack transport
    jQuery.ajaxTransport( "script", function( s ) {
    
        // This transport only deals with cross domain or forced-by-attrs requests
        if ( s.crossDomain || s.scriptAttrs ) {
            var script, callback;
            return {
                send: function( _, complete ) {
                    script = jQuery( "<script>" )
                        .attr( s.scriptAttrs || {} )
                        .prop( { charset: s.scriptCharset, src: s.url } )
                        .on( "load error", callback = function( evt ) {
                            script.remove();
                            callback = null;
                            if ( evt ) {
                                complete( evt.type === "error" ? 404 : 200, evt.type );
                            }
                        } );
    
                    // Use native DOM manipulation to avoid our domManip AJAX trickery
                    document.head.appendChild( script[ 0 ] );
                },
                abort: function() {
                    if ( callback ) {
                        callback();
                    }
                }
            };
        }
    } );
    
    
    
    
    var oldCallbacks = [],
        rjsonp = /(=)\?(?=&|$)|\?\?/;
    
    // Default jsonp settings
    jQuery.ajaxSetup( {
        jsonp: "callback",
        jsonpCallback: function() {
            var callback = oldCallbacks.pop() || ( jQuery.expando + "_" + ( nonce.guid++ ) );
            this[ callback ] = true;
            return callback;
        }
    } );
    
    // Detect, normalize options and install callbacks for jsonp requests
    jQuery.ajaxPrefilter( "json jsonp", function( s, originalSettings, jqXHR ) {
    
        var callbackName, overwritten, responseContainer,
            jsonProp = s.jsonp !== false && ( rjsonp.test( s.url ) ?
                "url" :
                typeof s.data === "string" &&
                    ( s.contentType || "" )
                        .indexOf( "application/x-www-form-urlencoded" ) === 0 &&
                    rjsonp.test( s.data ) && "data"
            );
    
        // Handle iff the expected data type is "jsonp" or we have a parameter to set
        if ( jsonProp || s.dataTypes[ 0 ] === "jsonp" ) {
    
            // Get callback name, remembering preexisting value associated with it
            callbackName = s.jsonpCallback = isFunction( s.jsonpCallback ) ?
                s.jsonpCallback() :
                s.jsonpCallback;
    
            // Insert callback into url or form data
            if ( jsonProp ) {
                s[ jsonProp ] = s[ jsonProp ].replace( rjsonp, "$1" + callbackName );
            } else if ( s.jsonp !== false ) {
                s.url += ( rquery.test( s.url ) ? "&" : "?" ) + s.jsonp + "=" + callbackName;
            }
    
            // Use data converter to retrieve json after script execution
            s.converters[ "script json" ] = function() {
                if ( !responseContainer ) {
                    jQuery.error( callbackName + " was not called" );
                }
                return responseContainer[ 0 ];
            };
    
            // Force json dataType
            s.dataTypes[ 0 ] = "json";
    
            // Install callback
            overwritten = window[ callbackName ];
            window[ callbackName ] = function() {
                responseContainer = arguments;
            };
    
            // Clean-up function (fires after converters)
            jqXHR.always( function() {
    
                // If previous value didn't exist - remove it
                if ( overwritten === undefined ) {
                    jQuery( window ).removeProp( callbackName );
    
                // Otherwise restore preexisting value
                } else {
                    window[ callbackName ] = overwritten;
                }
    
                // Save back as free
                if ( s[ callbackName ] ) {
    
                    // Make sure that re-using the options doesn't screw things around
                    s.jsonpCallback = originalSettings.jsonpCallback;
    
                    // Save the callback name for future use
                    oldCallbacks.push( callbackName );
                }
    
                // Call if it was a function and we have a response
                if ( responseContainer && isFunction( overwritten ) ) {
                    overwritten( responseContainer[ 0 ] );
                }
    
                responseContainer = overwritten = undefined;
            } );
    
            // Delegate to script
            return "script";
        }
    } );
    
    
    
    
    // Support: Safari 8 only
    // In Safari 8 documents created via document.implementation.createHTMLDocument
    // collapse sibling forms: the second one becomes a child of the first one.
    // Because of that, this security measure has to be disabled in Safari 8.
    // https://bugs.webkit.org/show_bug.cgi?id=137337
    support.createHTMLDocument = ( function() {
        var body = document.implementation.createHTMLDocument( "" ).body;
        body.innerHTML = "<form></form><form></form>";
        return body.childNodes.length === 2;
    } )();
    
    
    // Argument "data" should be string of html
    // context (optional): If specified, the fragment will be created in this context,
    // defaults to document
    // keepScripts (optional): If true, will include scripts passed in the html string
    jQuery.parseHTML = function( data, context, keepScripts ) {
        if ( typeof data !== "string" ) {
            return [];
        }
        if ( typeof context === "boolean" ) {
            keepScripts = context;
            context = false;
        }
    
        var base, parsed, scripts;
    
        if ( !context ) {
    
            // Stop scripts or inline event handlers from being executed immediately
            // by using document.implementation
            if ( support.createHTMLDocument ) {
                context = document.implementation.createHTMLDocument( "" );
    
                // Set the base href for the created document
                // so any parsed elements with URLs
                // are based on the document's URL (gh-2965)
                base = context.createElement( "base" );
                base.href = document.location.href;
                context.head.appendChild( base );
            } else {
                context = document;
            }
        }
    
        parsed = rsingleTag.exec( data );
        scripts = !keepScripts && [];
    
        // Single tag
        if ( parsed ) {
            return [ context.createElement( parsed[ 1 ] ) ];
        }
    
        parsed = buildFragment( [ data ], context, scripts );
    
        if ( scripts && scripts.length ) {
            jQuery( scripts ).remove();
        }
    
        return jQuery.merge( [], parsed.childNodes );
    };
    
    
    /**
     * Load a url into a page
     */
    jQuery.fn.load = function( url, params, callback ) {
        var selector, type, response,
            self = this,
            off = url.indexOf( " " );
    
        if ( off > -1 ) {
            selector = stripAndCollapse( url.slice( off ) );
            url = url.slice( 0, off );
        }
    
        // If it's a function
        if ( isFunction( params ) ) {
    
            // We assume that it's the callback
            callback = params;
            params = undefined;
    
        // Otherwise, build a param string
        } else if ( params && typeof params === "object" ) {
            type = "POST";
        }
    
        // If we have elements to modify, make the request
        if ( self.length > 0 ) {
            jQuery.ajax( {
                url: url,
    
                // If "type" variable is undefined, then "GET" method will be used.
                // Make value of this field explicit since
                // user can override it through ajaxSetup method
                type: type || "GET",
                dataType: "html",
                data: params
            } ).done( function( responseText ) {
    
                // Save response for use in complete callback
                response = arguments;
    
                self.html( selector ?
    
                    // If a selector was specified, locate the right elements in a dummy div
                    // Exclude scripts to avoid IE 'Permission Denied' errors
                    jQuery( "<div>" ).append( jQuery.parseHTML( responseText ) ).find( selector ) :
    
                    // Otherwise use the full result
                    responseText );
    
            // If the request succeeds, this function gets "data", "status", "jqXHR"
            // but they are ignored because response was set above.
            // If it fails, this function gets "jqXHR", "status", "error"
            } ).always( callback && function( jqXHR, status ) {
                self.each( function() {
                    callback.apply( this, response || [ jqXHR.responseText, status, jqXHR ] );
                } );
            } );
        }
    
        return this;
    };
    
    
    
    
    jQuery.expr.pseudos.animated = function( elem ) {
        return jQuery.grep( jQuery.timers, function( fn ) {
            return elem === fn.elem;
        } ).length;
    };
    
    
    
    
    jQuery.offset = {
        setOffset: function( elem, options, i ) {
            var curPosition, curLeft, curCSSTop, curTop, curOffset, curCSSLeft, calculatePosition,
                position = jQuery.css( elem, "position" ),
                curElem = jQuery( elem ),
                props = {};
    
            // Set position first, in-case top/left are set even on static elem
            if ( position === "static" ) {
                elem.style.position = "relative";
            }
    
            curOffset = curElem.offset();
            curCSSTop = jQuery.css( elem, "top" );
            curCSSLeft = jQuery.css( elem, "left" );
            calculatePosition = ( position === "absolute" || position === "fixed" ) &&
                ( curCSSTop + curCSSLeft ).indexOf( "auto" ) > -1;
    
            // Need to be able to calculate position if either
            // top or left is auto and position is either absolute or fixed
            if ( calculatePosition ) {
                curPosition = curElem.position();
                curTop = curPosition.top;
                curLeft = curPosition.left;
    
            } else {
                curTop = parseFloat( curCSSTop ) || 0;
                curLeft = parseFloat( curCSSLeft ) || 0;
            }
    
            if ( isFunction( options ) ) {
    
                // Use jQuery.extend here to allow modification of coordinates argument (gh-1848)
                options = options.call( elem, i, jQuery.extend( {}, curOffset ) );
            }
    
            if ( options.top != null ) {
                props.top = ( options.top - curOffset.top ) + curTop;
            }
            if ( options.left != null ) {
                props.left = ( options.left - curOffset.left ) + curLeft;
            }
    
            if ( "using" in options ) {
                options.using.call( elem, props );
    
            } else {
                curElem.css( props );
            }
        }
    };
    
    jQuery.fn.extend( {
    
        // offset() relates an element's border box to the document origin
        offset: function( options ) {
    
            // Preserve chaining for setter
            if ( arguments.length ) {
                return options === undefined ?
                    this :
                    this.each( function( i ) {
                        jQuery.offset.setOffset( this, options, i );
                    } );
            }
    
            var rect, win,
                elem = this[ 0 ];
    
            if ( !elem ) {
                return;
            }
    
            // Return zeros for disconnected and hidden (display: none) elements (gh-2310)
            // Support: IE <=11 only
            // Running getBoundingClientRect on a
            // disconnected node in IE throws an error
            if ( !elem.getClientRects().length ) {
                return { top: 0, left: 0 };
            }
    
            // Get document-relative position by adding viewport scroll to viewport-relative gBCR
            rect = elem.getBoundingClientRect();
            win = elem.ownerDocument.defaultView;
            return {
                top: rect.top + win.pageYOffset,
                left: rect.left + win.pageXOffset
            };
        },
    
        // position() relates an element's margin box to its offset parent's padding box
        // This corresponds to the behavior of CSS absolute positioning
        position: function() {
            if ( !this[ 0 ] ) {
                return;
            }
    
            var offsetParent, offset, doc,
                elem = this[ 0 ],
                parentOffset = { top: 0, left: 0 };
    
            // position:fixed elements are offset from the viewport, which itself always has zero offset
            if ( jQuery.css( elem, "position" ) === "fixed" ) {
    
                // Assume position:fixed implies availability of getBoundingClientRect
                offset = elem.getBoundingClientRect();
    
            } else {
                offset = this.offset();
    
                // Account for the *real* offset parent, which can be the document or its root element
                // when a statically positioned element is identified
                doc = elem.ownerDocument;
                offsetParent = elem.offsetParent || doc.documentElement;
                while ( offsetParent &&
                    ( offsetParent === doc.body || offsetParent === doc.documentElement ) &&
                    jQuery.css( offsetParent, "position" ) === "static" ) {
    
                    offsetParent = offsetParent.parentNode;
                }
                if ( offsetParent && offsetParent !== elem && offsetParent.nodeType === 1 ) {
    
                    // Incorporate borders into its offset, since they are outside its content origin
                    parentOffset = jQuery( offsetParent ).offset();
                    parentOffset.top += jQuery.css( offsetParent, "borderTopWidth", true );
                    parentOffset.left += jQuery.css( offsetParent, "borderLeftWidth", true );
                }
            }
    
            // Subtract parent offsets and element margins
            return {
                top: offset.top - parentOffset.top - jQuery.css( elem, "marginTop", true ),
                left: offset.left - parentOffset.left - jQuery.css( elem, "marginLeft", true )
            };
        },
    
        // This method will return documentElement in the following cases:
        // 1) For the element inside the iframe without offsetParent, this method will return
        //    documentElement of the parent window
        // 2) For the hidden or detached element
        // 3) For body or html element, i.e. in case of the html node - it will return itself
        //
        // but those exceptions were never presented as a real life use-cases
        // and might be considered as more preferable results.
        //
        // This logic, however, is not guaranteed and can change at any point in the future
        offsetParent: function() {
            return this.map( function() {
                var offsetParent = this.offsetParent;
    
                while ( offsetParent && jQuery.css( offsetParent, "position" ) === "static" ) {
                    offsetParent = offsetParent.offsetParent;
                }
    
                return offsetParent || documentElement;
            } );
        }
    } );
    
    // Create scrollLeft and scrollTop methods
    jQuery.each( { scrollLeft: "pageXOffset", scrollTop: "pageYOffset" }, function( method, prop ) {
        var top = "pageYOffset" === prop;
    
        jQuery.fn[ method ] = function( val ) {
            return access( this, function( elem, method, val ) {
    
                // Coalesce documents and windows
                var win;
                if ( isWindow( elem ) ) {
                    win = elem;
                } else if ( elem.nodeType === 9 ) {
                    win = elem.defaultView;
                }
    
                if ( val === undefined ) {
                    return win ? win[ prop ] : elem[ method ];
                }
    
                if ( win ) {
                    win.scrollTo(
                        !top ? val : win.pageXOffset,
                        top ? val : win.pageYOffset
                    );
    
                } else {
                    elem[ method ] = val;
                }
            }, method, val, arguments.length );
        };
    } );
    
    // Support: Safari <=7 - 9.1, Chrome <=37 - 49
    // Add the top/left cssHooks using jQuery.fn.position
    // Webkit bug: https://bugs.webkit.org/show_bug.cgi?id=29084
    // Blink bug: https://bugs.chromium.org/p/chromium/issues/detail?id=589347
    // getComputedStyle returns percent when specified for top/left/bottom/right;
    // rather than make the css module depend on the offset module, just check for it here
    jQuery.each( [ "top", "left" ], function( _i, prop ) {
        jQuery.cssHooks[ prop ] = addGetHookIf( support.pixelPosition,
            function( elem, computed ) {
                if ( computed ) {
                    computed = curCSS( elem, prop );
    
                    // If curCSS returns percentage, fallback to offset
                    return rnumnonpx.test( computed ) ?
                        jQuery( elem ).position()[ prop ] + "px" :
                        computed;
                }
            }
        );
    } );
    
    
    // Create innerHeight, innerWidth, height, width, outerHeight and outerWidth methods
    jQuery.each( { Height: "height", Width: "width" }, function( name, type ) {
        jQuery.each( {
            padding: "inner" + name,
            content: type,
            "": "outer" + name
        }, function( defaultExtra, funcName ) {
    
            // Margin is only for outerHeight, outerWidth
            jQuery.fn[ funcName ] = function( margin, value ) {
                var chainable = arguments.length && ( defaultExtra || typeof margin !== "boolean" ),
                    extra = defaultExtra || ( margin === true || value === true ? "margin" : "border" );
    
                return access( this, function( elem, type, value ) {
                    var doc;
    
                    if ( isWindow( elem ) ) {
    
                        // $( window ).outerWidth/Height return w/h including scrollbars (gh-1729)
                        return funcName.indexOf( "outer" ) === 0 ?
                            elem[ "inner" + name ] :
                            elem.document.documentElement[ "client" + name ];
                    }
    
                    // Get document width or height
                    if ( elem.nodeType === 9 ) {
                        doc = elem.documentElement;
    
                        // Either scroll[Width/Height] or offset[Width/Height] or client[Width/Height],
                        // whichever is greatest
                        return Math.max(
                            elem.body[ "scroll" + name ], doc[ "scroll" + name ],
                            elem.body[ "offset" + name ], doc[ "offset" + name ],
                            doc[ "client" + name ]
                        );
                    }
    
                    return value === undefined ?
    
                        // Get width or height on the element, requesting but not forcing parseFloat
                        jQuery.css( elem, type, extra ) :
    
                        // Set width or height on the element
                        jQuery.style( elem, type, value, extra );
                }, type, chainable ? margin : undefined, chainable );
            };
        } );
    } );
    
    
    jQuery.each( [
        "ajaxStart",
        "ajaxStop",
        "ajaxComplete",
        "ajaxError",
        "ajaxSuccess",
        "ajaxSend"
    ], function( _i, type ) {
        jQuery.fn[ type ] = function( fn ) {
            return this.on( type, fn );
        };
    } );
    
    
    
    
    jQuery.fn.extend( {
    
        bind: function( types, data, fn ) {
            return this.on( types, null, data, fn );
        },
        unbind: function( types, fn ) {
            return this.off( types, null, fn );
        },
    
        delegate: function( selector, types, data, fn ) {
            return this.on( types, selector, data, fn );
        },
        undelegate: function( selector, types, fn ) {
    
            // ( namespace ) or ( selector, types [, fn] )
            return arguments.length === 1 ?
                this.off( selector, "**" ) :
                this.off( types, selector || "**", fn );
        },
    
        hover: function( fnOver, fnOut ) {
            return this.mouseenter( fnOver ).mouseleave( fnOut || fnOver );
        }
    } );
    
    jQuery.each(
        ( "blur focus focusin focusout resize scroll click dblclick " +
        "mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave " +
        "change select submit keydown keypress keyup contextmenu" ).split( " " ),
        function( _i, name ) {
    
            // Handle event binding
            jQuery.fn[ name ] = function( data, fn ) {
                return arguments.length > 0 ?
                    this.on( name, null, data, fn ) :
                    this.trigger( name );
            };
        }
    );
    
    
    
    
    // Support: Android <=4.0 only
    // Make sure we trim BOM and NBSP
    var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
    
    // Bind a function to a context, optionally partially applying any
    // arguments.
    // jQuery.proxy is deprecated to promote standards (specifically Function#bind)
    // However, it is not slated for removal any time soon
    jQuery.proxy = function( fn, context ) {
        var tmp, args, proxy;
    
        if ( typeof context === "string" ) {
            tmp = fn[ context ];
            context = fn;
            fn = tmp;
        }
    
        // Quick check to determine if target is callable, in the spec
        // this throws a TypeError, but we will just return undefined.
        if ( !isFunction( fn ) ) {
            return undefined;
        }
    
        // Simulated bind
        args = slice.call( arguments, 2 );
        proxy = function() {
            return fn.apply( context || this, args.concat( slice.call( arguments ) ) );
        };
    
        // Set the guid of unique handler to the same of original handler, so it can be removed
        proxy.guid = fn.guid = fn.guid || jQuery.guid++;
    
        return proxy;
    };
    
    jQuery.holdReady = function( hold ) {
        if ( hold ) {
            jQuery.readyWait++;
        } else {
            jQuery.ready( true );
        }
    };
    jQuery.isArray = Array.isArray;
    jQuery.parseJSON = JSON.parse;
    jQuery.nodeName = nodeName;
    jQuery.isFunction = isFunction;
    jQuery.isWindow = isWindow;
    jQuery.camelCase = camelCase;
    jQuery.type = toType;
    
    jQuery.now = Date.now;
    
    jQuery.isNumeric = function( obj ) {
    
        // As of jQuery 3.0, isNumeric is limited to
        // strings and numbers (primitives or objects)
        // that can be coerced to finite numbers (gh-2662)
        var type = jQuery.type( obj );
        return ( type === "number" || type === "string" ) &&
    
            // parseFloat NaNs numeric-cast false positives ("")
            // ...but misinterprets leading-number strings, particularly hex literals ("0x...")
            // subtraction forces infinities to NaN
            !isNaN( obj - parseFloat( obj ) );
    };
    
    jQuery.trim = function( text ) {
        return text == null ?
            "" :
            ( text + "" ).replace( rtrim, "" );
    };
    
    
    
    // Register as a named AMD module, since jQuery can be concatenated with other
    // files that may use define, but not via a proper concatenation script that
    // understands anonymous AMD modules. A named AMD is safest and most robust
    // way to register. Lowercase jquery is used because AMD module names are
    // derived from file names, and jQuery is normally delivered in a lowercase
    // file name. Do this after creating the global so that if an AMD module wants
    // to call noConflict to hide this version of jQuery, it will work.
    
    // Note that for maximum portability, libraries that are not jQuery should
    // declare themselves as anonymous modules, and avoid setting a global if an
    // AMD loader is present. jQuery is a special case. For more information, see
    // https://github.com/jrburke/requirejs/wiki/Updating-existing-libraries#wiki-anon
    
    if ( true ) {
        !(__WEBPACK_AMD_DEFINE_ARRAY__ = [], __WEBPACK_AMD_DEFINE_RESULT__ = (function() {
            return jQuery;
        }).apply(exports, __WEBPACK_AMD_DEFINE_ARRAY__),
                    __WEBPACK_AMD_DEFINE_RESULT__ !== undefined && (module.exports = __WEBPACK_AMD_DEFINE_RESULT__));
    }
    
    
    
    
    var
    
        // Map over jQuery in case of overwrite
        _jQuery = window.jQuery,
    
        // Map over the $ in case of overwrite
        _$ = window.$;
    
    jQuery.noConflict = function( deep ) {
        if ( window.$ === jQuery ) {
            window.$ = _$;
        }
    
        if ( deep && window.jQuery === jQuery ) {
            window.jQuery = _jQuery;
        }
    
        return jQuery;
    };
    
    // Expose jQuery and $ identifiers, even in AMD
    // (#7102#comment:10, https://github.com/jquery/jquery/pull/557)
    // and CommonJS for browser emulators (#13566)
    if ( typeof noGlobal === "undefined" ) {
        window.jQuery = window.$ = jQuery;
    }
    
    
    
    
    return jQuery;
    } );
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_DataView.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_DataView.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getNative = __webpack_require__(/*! ./_getNative */ "./node_modules/lodash/_getNative.js"),
        root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /* Built-in method references that are verified to be native. */
    var DataView = getNative(root, 'DataView');
    
    module.exports = DataView;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_Hash.js":
    /*!**************************************!*\
      !*** ./node_modules/lodash/_Hash.js ***!
      \**************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var hashClear = __webpack_require__(/*! ./_hashClear */ "./node_modules/lodash/_hashClear.js"),
        hashDelete = __webpack_require__(/*! ./_hashDelete */ "./node_modules/lodash/_hashDelete.js"),
        hashGet = __webpack_require__(/*! ./_hashGet */ "./node_modules/lodash/_hashGet.js"),
        hashHas = __webpack_require__(/*! ./_hashHas */ "./node_modules/lodash/_hashHas.js"),
        hashSet = __webpack_require__(/*! ./_hashSet */ "./node_modules/lodash/_hashSet.js");
    
    /**
     * Creates a hash object.
     *
     * @private
     * @constructor
     * @param {Array} [entries] The key-value pairs to cache.
     */
    function Hash(entries) {
      var index = -1,
          length = entries == null ? 0 : entries.length;
    
      this.clear();
      while (++index < length) {
        var entry = entries[index];
        this.set(entry[0], entry[1]);
      }
    }
    
    // Add methods to `Hash`.
    Hash.prototype.clear = hashClear;
    Hash.prototype['delete'] = hashDelete;
    Hash.prototype.get = hashGet;
    Hash.prototype.has = hashHas;
    Hash.prototype.set = hashSet;
    
    module.exports = Hash;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_ListCache.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_ListCache.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var listCacheClear = __webpack_require__(/*! ./_listCacheClear */ "./node_modules/lodash/_listCacheClear.js"),
        listCacheDelete = __webpack_require__(/*! ./_listCacheDelete */ "./node_modules/lodash/_listCacheDelete.js"),
        listCacheGet = __webpack_require__(/*! ./_listCacheGet */ "./node_modules/lodash/_listCacheGet.js"),
        listCacheHas = __webpack_require__(/*! ./_listCacheHas */ "./node_modules/lodash/_listCacheHas.js"),
        listCacheSet = __webpack_require__(/*! ./_listCacheSet */ "./node_modules/lodash/_listCacheSet.js");
    
    /**
     * Creates an list cache object.
     *
     * @private
     * @constructor
     * @param {Array} [entries] The key-value pairs to cache.
     */
    function ListCache(entries) {
      var index = -1,
          length = entries == null ? 0 : entries.length;
    
      this.clear();
      while (++index < length) {
        var entry = entries[index];
        this.set(entry[0], entry[1]);
      }
    }
    
    // Add methods to `ListCache`.
    ListCache.prototype.clear = listCacheClear;
    ListCache.prototype['delete'] = listCacheDelete;
    ListCache.prototype.get = listCacheGet;
    ListCache.prototype.has = listCacheHas;
    ListCache.prototype.set = listCacheSet;
    
    module.exports = ListCache;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_Map.js":
    /*!*************************************!*\
      !*** ./node_modules/lodash/_Map.js ***!
      \*************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getNative = __webpack_require__(/*! ./_getNative */ "./node_modules/lodash/_getNative.js"),
        root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /* Built-in method references that are verified to be native. */
    var Map = getNative(root, 'Map');
    
    module.exports = Map;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_MapCache.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_MapCache.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var mapCacheClear = __webpack_require__(/*! ./_mapCacheClear */ "./node_modules/lodash/_mapCacheClear.js"),
        mapCacheDelete = __webpack_require__(/*! ./_mapCacheDelete */ "./node_modules/lodash/_mapCacheDelete.js"),
        mapCacheGet = __webpack_require__(/*! ./_mapCacheGet */ "./node_modules/lodash/_mapCacheGet.js"),
        mapCacheHas = __webpack_require__(/*! ./_mapCacheHas */ "./node_modules/lodash/_mapCacheHas.js"),
        mapCacheSet = __webpack_require__(/*! ./_mapCacheSet */ "./node_modules/lodash/_mapCacheSet.js");
    
    /**
     * Creates a map cache object to store key-value pairs.
     *
     * @private
     * @constructor
     * @param {Array} [entries] The key-value pairs to cache.
     */
    function MapCache(entries) {
      var index = -1,
          length = entries == null ? 0 : entries.length;
    
      this.clear();
      while (++index < length) {
        var entry = entries[index];
        this.set(entry[0], entry[1]);
      }
    }
    
    // Add methods to `MapCache`.
    MapCache.prototype.clear = mapCacheClear;
    MapCache.prototype['delete'] = mapCacheDelete;
    MapCache.prototype.get = mapCacheGet;
    MapCache.prototype.has = mapCacheHas;
    MapCache.prototype.set = mapCacheSet;
    
    module.exports = MapCache;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_Promise.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_Promise.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getNative = __webpack_require__(/*! ./_getNative */ "./node_modules/lodash/_getNative.js"),
        root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /* Built-in method references that are verified to be native. */
    var Promise = getNative(root, 'Promise');
    
    module.exports = Promise;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_Set.js":
    /*!*************************************!*\
      !*** ./node_modules/lodash/_Set.js ***!
      \*************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getNative = __webpack_require__(/*! ./_getNative */ "./node_modules/lodash/_getNative.js"),
        root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /* Built-in method references that are verified to be native. */
    var Set = getNative(root, 'Set');
    
    module.exports = Set;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_SetCache.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_SetCache.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var MapCache = __webpack_require__(/*! ./_MapCache */ "./node_modules/lodash/_MapCache.js"),
        setCacheAdd = __webpack_require__(/*! ./_setCacheAdd */ "./node_modules/lodash/_setCacheAdd.js"),
        setCacheHas = __webpack_require__(/*! ./_setCacheHas */ "./node_modules/lodash/_setCacheHas.js");
    
    /**
     *
     * Creates an array cache object to store unique values.
     *
     * @private
     * @constructor
     * @param {Array} [values] The values to cache.
     */
    function SetCache(values) {
      var index = -1,
          length = values == null ? 0 : values.length;
    
      this.__data__ = new MapCache;
      while (++index < length) {
        this.add(values[index]);
      }
    }
    
    // Add methods to `SetCache`.
    SetCache.prototype.add = SetCache.prototype.push = setCacheAdd;
    SetCache.prototype.has = setCacheHas;
    
    module.exports = SetCache;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_Stack.js":
    /*!***************************************!*\
      !*** ./node_modules/lodash/_Stack.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var ListCache = __webpack_require__(/*! ./_ListCache */ "./node_modules/lodash/_ListCache.js"),
        stackClear = __webpack_require__(/*! ./_stackClear */ "./node_modules/lodash/_stackClear.js"),
        stackDelete = __webpack_require__(/*! ./_stackDelete */ "./node_modules/lodash/_stackDelete.js"),
        stackGet = __webpack_require__(/*! ./_stackGet */ "./node_modules/lodash/_stackGet.js"),
        stackHas = __webpack_require__(/*! ./_stackHas */ "./node_modules/lodash/_stackHas.js"),
        stackSet = __webpack_require__(/*! ./_stackSet */ "./node_modules/lodash/_stackSet.js");
    
    /**
     * Creates a stack cache object to store key-value pairs.
     *
     * @private
     * @constructor
     * @param {Array} [entries] The key-value pairs to cache.
     */
    function Stack(entries) {
      var data = this.__data__ = new ListCache(entries);
      this.size = data.size;
    }
    
    // Add methods to `Stack`.
    Stack.prototype.clear = stackClear;
    Stack.prototype['delete'] = stackDelete;
    Stack.prototype.get = stackGet;
    Stack.prototype.has = stackHas;
    Stack.prototype.set = stackSet;
    
    module.exports = Stack;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_Symbol.js":
    /*!****************************************!*\
      !*** ./node_modules/lodash/_Symbol.js ***!
      \****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /** Built-in value references. */
    var Symbol = root.Symbol;
    
    module.exports = Symbol;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_Uint8Array.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_Uint8Array.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /** Built-in value references. */
    var Uint8Array = root.Uint8Array;
    
    module.exports = Uint8Array;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_WeakMap.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_WeakMap.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getNative = __webpack_require__(/*! ./_getNative */ "./node_modules/lodash/_getNative.js"),
        root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /* Built-in method references that are verified to be native. */
    var WeakMap = getNative(root, 'WeakMap');
    
    module.exports = WeakMap;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_apply.js":
    /*!***************************************!*\
      !*** ./node_modules/lodash/_apply.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * A faster alternative to `Function#apply`, this function invokes `func`
     * with the `this` binding of `thisArg` and the arguments of `args`.
     *
     * @private
     * @param {Function} func The function to invoke.
     * @param {*} thisArg The `this` binding of `func`.
     * @param {Array} args The arguments to invoke `func` with.
     * @returns {*} Returns the result of `func`.
     */
    function apply(func, thisArg, args) {
      switch (args.length) {
        case 0: return func.call(thisArg);
        case 1: return func.call(thisArg, args[0]);
        case 2: return func.call(thisArg, args[0], args[1]);
        case 3: return func.call(thisArg, args[0], args[1], args[2]);
      }
      return func.apply(thisArg, args);
    }
    
    module.exports = apply;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_arrayEach.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_arrayEach.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * A specialized version of `_.forEach` for arrays without support for
     * iteratee shorthands.
     *
     * @private
     * @param {Array} [array] The array to iterate over.
     * @param {Function} iteratee The function invoked per iteration.
     * @returns {Array} Returns `array`.
     */
    function arrayEach(array, iteratee) {
      var index = -1,
          length = array == null ? 0 : array.length;
    
      while (++index < length) {
        if (iteratee(array[index], index, array) === false) {
          break;
        }
      }
      return array;
    }
    
    module.exports = arrayEach;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_arrayFilter.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_arrayFilter.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * A specialized version of `_.filter` for arrays without support for
     * iteratee shorthands.
     *
     * @private
     * @param {Array} [array] The array to iterate over.
     * @param {Function} predicate The function invoked per iteration.
     * @returns {Array} Returns the new filtered array.
     */
    function arrayFilter(array, predicate) {
      var index = -1,
          length = array == null ? 0 : array.length,
          resIndex = 0,
          result = [];
    
      while (++index < length) {
        var value = array[index];
        if (predicate(value, index, array)) {
          result[resIndex++] = value;
        }
      }
      return result;
    }
    
    module.exports = arrayFilter;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_arrayLikeKeys.js":
    /*!***********************************************!*\
      !*** ./node_modules/lodash/_arrayLikeKeys.js ***!
      \***********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseTimes = __webpack_require__(/*! ./_baseTimes */ "./node_modules/lodash/_baseTimes.js"),
        isArguments = __webpack_require__(/*! ./isArguments */ "./node_modules/lodash/isArguments.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        isBuffer = __webpack_require__(/*! ./isBuffer */ "./node_modules/lodash/isBuffer.js"),
        isIndex = __webpack_require__(/*! ./_isIndex */ "./node_modules/lodash/_isIndex.js"),
        isTypedArray = __webpack_require__(/*! ./isTypedArray */ "./node_modules/lodash/isTypedArray.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * Creates an array of the enumerable property names of the array-like `value`.
     *
     * @private
     * @param {*} value The value to query.
     * @param {boolean} inherited Specify returning inherited property names.
     * @returns {Array} Returns the array of property names.
     */
    function arrayLikeKeys(value, inherited) {
      var isArr = isArray(value),
          isArg = !isArr && isArguments(value),
          isBuff = !isArr && !isArg && isBuffer(value),
          isType = !isArr && !isArg && !isBuff && isTypedArray(value),
          skipIndexes = isArr || isArg || isBuff || isType,
          result = skipIndexes ? baseTimes(value.length, String) : [],
          length = result.length;
    
      for (var key in value) {
        if ((inherited || hasOwnProperty.call(value, key)) &&
            !(skipIndexes && (
               // Safari 9 has enumerable `arguments.length` in strict mode.
               key == 'length' ||
               // Node.js 0.10 has enumerable non-index properties on buffers.
               (isBuff && (key == 'offset' || key == 'parent')) ||
               // PhantomJS 2 has enumerable non-index properties on typed arrays.
               (isType && (key == 'buffer' || key == 'byteLength' || key == 'byteOffset')) ||
               // Skip index properties.
               isIndex(key, length)
            ))) {
          result.push(key);
        }
      }
      return result;
    }
    
    module.exports = arrayLikeKeys;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_arrayMap.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_arrayMap.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * A specialized version of `_.map` for arrays without support for iteratee
     * shorthands.
     *
     * @private
     * @param {Array} [array] The array to iterate over.
     * @param {Function} iteratee The function invoked per iteration.
     * @returns {Array} Returns the new mapped array.
     */
    function arrayMap(array, iteratee) {
      var index = -1,
          length = array == null ? 0 : array.length,
          result = Array(length);
    
      while (++index < length) {
        result[index] = iteratee(array[index], index, array);
      }
      return result;
    }
    
    module.exports = arrayMap;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_arrayPush.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_arrayPush.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Appends the elements of `values` to `array`.
     *
     * @private
     * @param {Array} array The array to modify.
     * @param {Array} values The values to append.
     * @returns {Array} Returns `array`.
     */
    function arrayPush(array, values) {
      var index = -1,
          length = values.length,
          offset = array.length;
    
      while (++index < length) {
        array[offset + index] = values[index];
      }
      return array;
    }
    
    module.exports = arrayPush;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_arrayReduce.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_arrayReduce.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * A specialized version of `_.reduce` for arrays without support for
     * iteratee shorthands.
     *
     * @private
     * @param {Array} [array] The array to iterate over.
     * @param {Function} iteratee The function invoked per iteration.
     * @param {*} [accumulator] The initial value.
     * @param {boolean} [initAccum] Specify using the first element of `array` as
     *  the initial value.
     * @returns {*} Returns the accumulated value.
     */
    function arrayReduce(array, iteratee, accumulator, initAccum) {
      var index = -1,
          length = array == null ? 0 : array.length;
    
      if (initAccum && length) {
        accumulator = array[++index];
      }
      while (++index < length) {
        accumulator = iteratee(accumulator, array[index], index, array);
      }
      return accumulator;
    }
    
    module.exports = arrayReduce;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_arraySome.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_arraySome.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * A specialized version of `_.some` for arrays without support for iteratee
     * shorthands.
     *
     * @private
     * @param {Array} [array] The array to iterate over.
     * @param {Function} predicate The function invoked per iteration.
     * @returns {boolean} Returns `true` if any element passes the predicate check,
     *  else `false`.
     */
    function arraySome(array, predicate) {
      var index = -1,
          length = array == null ? 0 : array.length;
    
      while (++index < length) {
        if (predicate(array[index], index, array)) {
          return true;
        }
      }
      return false;
    }
    
    module.exports = arraySome;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_asciiToArray.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_asciiToArray.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Converts an ASCII `string` to an array.
     *
     * @private
     * @param {string} string The string to convert.
     * @returns {Array} Returns the converted array.
     */
    function asciiToArray(string) {
      return string.split('');
    }
    
    module.exports = asciiToArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_asciiWords.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_asciiWords.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used to match words composed of alphanumeric characters. */
    var reAsciiWord = /[^\x00-\x2f\x3a-\x40\x5b-\x60\x7b-\x7f]+/g;
    
    /**
     * Splits an ASCII `string` into an array of its words.
     *
     * @private
     * @param {string} The string to inspect.
     * @returns {Array} Returns the words of `string`.
     */
    function asciiWords(string) {
      return string.match(reAsciiWord) || [];
    }
    
    module.exports = asciiWords;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_assocIndexOf.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_assocIndexOf.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var eq = __webpack_require__(/*! ./eq */ "./node_modules/lodash/eq.js");
    
    /**
     * Gets the index at which the `key` is found in `array` of key-value pairs.
     *
     * @private
     * @param {Array} array The array to inspect.
     * @param {*} key The key to search for.
     * @returns {number} Returns the index of the matched value, else `-1`.
     */
    function assocIndexOf(array, key) {
      var length = array.length;
      while (length--) {
        if (eq(array[length][0], key)) {
          return length;
        }
      }
      return -1;
    }
    
    module.exports = assocIndexOf;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseEach.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_baseEach.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseForOwn = __webpack_require__(/*! ./_baseForOwn */ "./node_modules/lodash/_baseForOwn.js"),
        createBaseEach = __webpack_require__(/*! ./_createBaseEach */ "./node_modules/lodash/_createBaseEach.js");
    
    /**
     * The base implementation of `_.forEach` without support for iteratee shorthands.
     *
     * @private
     * @param {Array|Object} collection The collection to iterate over.
     * @param {Function} iteratee The function invoked per iteration.
     * @returns {Array|Object} Returns `collection`.
     */
    var baseEach = createBaseEach(baseForOwn);
    
    module.exports = baseEach;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseFilter.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_baseFilter.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseEach = __webpack_require__(/*! ./_baseEach */ "./node_modules/lodash/_baseEach.js");
    
    /**
     * The base implementation of `_.filter` without support for iteratee shorthands.
     *
     * @private
     * @param {Array|Object} collection The collection to iterate over.
     * @param {Function} predicate The function invoked per iteration.
     * @returns {Array} Returns the new filtered array.
     */
    function baseFilter(collection, predicate) {
      var result = [];
      baseEach(collection, function(value, index, collection) {
        if (predicate(value, index, collection)) {
          result.push(value);
        }
      });
      return result;
    }
    
    module.exports = baseFilter;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseFor.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_baseFor.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var createBaseFor = __webpack_require__(/*! ./_createBaseFor */ "./node_modules/lodash/_createBaseFor.js");
    
    /**
     * The base implementation of `baseForOwn` which iterates over `object`
     * properties returned by `keysFunc` and invokes `iteratee` for each property.
     * Iteratee functions may exit iteration early by explicitly returning `false`.
     *
     * @private
     * @param {Object} object The object to iterate over.
     * @param {Function} iteratee The function invoked per iteration.
     * @param {Function} keysFunc The function to get the keys of `object`.
     * @returns {Object} Returns `object`.
     */
    var baseFor = createBaseFor();
    
    module.exports = baseFor;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseForOwn.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_baseForOwn.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseFor = __webpack_require__(/*! ./_baseFor */ "./node_modules/lodash/_baseFor.js"),
        keys = __webpack_require__(/*! ./keys */ "./node_modules/lodash/keys.js");
    
    /**
     * The base implementation of `_.forOwn` without support for iteratee shorthands.
     *
     * @private
     * @param {Object} object The object to iterate over.
     * @param {Function} iteratee The function invoked per iteration.
     * @returns {Object} Returns `object`.
     */
    function baseForOwn(object, iteratee) {
      return object && baseFor(object, iteratee, keys);
    }
    
    module.exports = baseForOwn;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseGet.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_baseGet.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var castPath = __webpack_require__(/*! ./_castPath */ "./node_modules/lodash/_castPath.js"),
        toKey = __webpack_require__(/*! ./_toKey */ "./node_modules/lodash/_toKey.js");
    
    /**
     * The base implementation of `_.get` without support for default values.
     *
     * @private
     * @param {Object} object The object to query.
     * @param {Array|string} path The path of the property to get.
     * @returns {*} Returns the resolved value.
     */
    function baseGet(object, path) {
      path = castPath(path, object);
    
      var index = 0,
          length = path.length;
    
      while (object != null && index < length) {
        object = object[toKey(path[index++])];
      }
      return (index && index == length) ? object : undefined;
    }
    
    module.exports = baseGet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseGetAllKeys.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_baseGetAllKeys.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var arrayPush = __webpack_require__(/*! ./_arrayPush */ "./node_modules/lodash/_arrayPush.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js");
    
    /**
     * The base implementation of `getAllKeys` and `getAllKeysIn` which uses
     * `keysFunc` and `symbolsFunc` to get the enumerable property names and
     * symbols of `object`.
     *
     * @private
     * @param {Object} object The object to query.
     * @param {Function} keysFunc The function to get the keys of `object`.
     * @param {Function} symbolsFunc The function to get the symbols of `object`.
     * @returns {Array} Returns the array of property names and symbols.
     */
    function baseGetAllKeys(object, keysFunc, symbolsFunc) {
      var result = keysFunc(object);
      return isArray(object) ? result : arrayPush(result, symbolsFunc(object));
    }
    
    module.exports = baseGetAllKeys;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseGetTag.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_baseGetTag.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var Symbol = __webpack_require__(/*! ./_Symbol */ "./node_modules/lodash/_Symbol.js"),
        getRawTag = __webpack_require__(/*! ./_getRawTag */ "./node_modules/lodash/_getRawTag.js"),
        objectToString = __webpack_require__(/*! ./_objectToString */ "./node_modules/lodash/_objectToString.js");
    
    /** `Object#toString` result references. */
    var nullTag = '[object Null]',
        undefinedTag = '[object Undefined]';
    
    /** Built-in value references. */
    var symToStringTag = Symbol ? Symbol.toStringTag : undefined;
    
    /**
     * The base implementation of `getTag` without fallbacks for buggy environments.
     *
     * @private
     * @param {*} value The value to query.
     * @returns {string} Returns the `toStringTag`.
     */
    function baseGetTag(value) {
      if (value == null) {
        return value === undefined ? undefinedTag : nullTag;
      }
      return (symToStringTag && symToStringTag in Object(value))
        ? getRawTag(value)
        : objectToString(value);
    }
    
    module.exports = baseGetTag;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseHasIn.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_baseHasIn.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * The base implementation of `_.hasIn` without support for deep paths.
     *
     * @private
     * @param {Object} [object] The object to query.
     * @param {Array|string} key The key to check.
     * @returns {boolean} Returns `true` if `key` exists, else `false`.
     */
    function baseHasIn(object, key) {
      return object != null && key in Object(object);
    }
    
    module.exports = baseHasIn;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseIsArguments.js":
    /*!*************************************************!*\
      !*** ./node_modules/lodash/_baseIsArguments.js ***!
      \*************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ "./node_modules/lodash/_baseGetTag.js"),
        isObjectLike = __webpack_require__(/*! ./isObjectLike */ "./node_modules/lodash/isObjectLike.js");
    
    /** `Object#toString` result references. */
    var argsTag = '[object Arguments]';
    
    /**
     * The base implementation of `_.isArguments`.
     *
     * @private
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is an `arguments` object,
     */
    function baseIsArguments(value) {
      return isObjectLike(value) && baseGetTag(value) == argsTag;
    }
    
    module.exports = baseIsArguments;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseIsEqual.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_baseIsEqual.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseIsEqualDeep = __webpack_require__(/*! ./_baseIsEqualDeep */ "./node_modules/lodash/_baseIsEqualDeep.js"),
        isObjectLike = __webpack_require__(/*! ./isObjectLike */ "./node_modules/lodash/isObjectLike.js");
    
    /**
     * The base implementation of `_.isEqual` which supports partial comparisons
     * and tracks traversed objects.
     *
     * @private
     * @param {*} value The value to compare.
     * @param {*} other The other value to compare.
     * @param {boolean} bitmask The bitmask flags.
     *  1 - Unordered comparison
     *  2 - Partial comparison
     * @param {Function} [customizer] The function to customize comparisons.
     * @param {Object} [stack] Tracks traversed `value` and `other` objects.
     * @returns {boolean} Returns `true` if the values are equivalent, else `false`.
     */
    function baseIsEqual(value, other, bitmask, customizer, stack) {
      if (value === other) {
        return true;
      }
      if (value == null || other == null || (!isObjectLike(value) && !isObjectLike(other))) {
        return value !== value && other !== other;
      }
      return baseIsEqualDeep(value, other, bitmask, customizer, baseIsEqual, stack);
    }
    
    module.exports = baseIsEqual;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseIsEqualDeep.js":
    /*!*************************************************!*\
      !*** ./node_modules/lodash/_baseIsEqualDeep.js ***!
      \*************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var Stack = __webpack_require__(/*! ./_Stack */ "./node_modules/lodash/_Stack.js"),
        equalArrays = __webpack_require__(/*! ./_equalArrays */ "./node_modules/lodash/_equalArrays.js"),
        equalByTag = __webpack_require__(/*! ./_equalByTag */ "./node_modules/lodash/_equalByTag.js"),
        equalObjects = __webpack_require__(/*! ./_equalObjects */ "./node_modules/lodash/_equalObjects.js"),
        getTag = __webpack_require__(/*! ./_getTag */ "./node_modules/lodash/_getTag.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        isBuffer = __webpack_require__(/*! ./isBuffer */ "./node_modules/lodash/isBuffer.js"),
        isTypedArray = __webpack_require__(/*! ./isTypedArray */ "./node_modules/lodash/isTypedArray.js");
    
    /** Used to compose bitmasks for value comparisons. */
    var COMPARE_PARTIAL_FLAG = 1;
    
    /** `Object#toString` result references. */
    var argsTag = '[object Arguments]',
        arrayTag = '[object Array]',
        objectTag = '[object Object]';
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * A specialized version of `baseIsEqual` for arrays and objects which performs
     * deep comparisons and tracks traversed objects enabling objects with circular
     * references to be compared.
     *
     * @private
     * @param {Object} object The object to compare.
     * @param {Object} other The other object to compare.
     * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
     * @param {Function} customizer The function to customize comparisons.
     * @param {Function} equalFunc The function to determine equivalents of values.
     * @param {Object} [stack] Tracks traversed `object` and `other` objects.
     * @returns {boolean} Returns `true` if the objects are equivalent, else `false`.
     */
    function baseIsEqualDeep(object, other, bitmask, customizer, equalFunc, stack) {
      var objIsArr = isArray(object),
          othIsArr = isArray(other),
          objTag = objIsArr ? arrayTag : getTag(object),
          othTag = othIsArr ? arrayTag : getTag(other);
    
      objTag = objTag == argsTag ? objectTag : objTag;
      othTag = othTag == argsTag ? objectTag : othTag;
    
      var objIsObj = objTag == objectTag,
          othIsObj = othTag == objectTag,
          isSameTag = objTag == othTag;
    
      if (isSameTag && isBuffer(object)) {
        if (!isBuffer(other)) {
          return false;
        }
        objIsArr = true;
        objIsObj = false;
      }
      if (isSameTag && !objIsObj) {
        stack || (stack = new Stack);
        return (objIsArr || isTypedArray(object))
          ? equalArrays(object, other, bitmask, customizer, equalFunc, stack)
          : equalByTag(object, other, objTag, bitmask, customizer, equalFunc, stack);
      }
      if (!(bitmask & COMPARE_PARTIAL_FLAG)) {
        var objIsWrapped = objIsObj && hasOwnProperty.call(object, '__wrapped__'),
            othIsWrapped = othIsObj && hasOwnProperty.call(other, '__wrapped__');
    
        if (objIsWrapped || othIsWrapped) {
          var objUnwrapped = objIsWrapped ? object.value() : object,
              othUnwrapped = othIsWrapped ? other.value() : other;
    
          stack || (stack = new Stack);
          return equalFunc(objUnwrapped, othUnwrapped, bitmask, customizer, stack);
        }
      }
      if (!isSameTag) {
        return false;
      }
      stack || (stack = new Stack);
      return equalObjects(object, other, bitmask, customizer, equalFunc, stack);
    }
    
    module.exports = baseIsEqualDeep;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseIsMatch.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_baseIsMatch.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var Stack = __webpack_require__(/*! ./_Stack */ "./node_modules/lodash/_Stack.js"),
        baseIsEqual = __webpack_require__(/*! ./_baseIsEqual */ "./node_modules/lodash/_baseIsEqual.js");
    
    /** Used to compose bitmasks for value comparisons. */
    var COMPARE_PARTIAL_FLAG = 1,
        COMPARE_UNORDERED_FLAG = 2;
    
    /**
     * The base implementation of `_.isMatch` without support for iteratee shorthands.
     *
     * @private
     * @param {Object} object The object to inspect.
     * @param {Object} source The object of property values to match.
     * @param {Array} matchData The property names, values, and compare flags to match.
     * @param {Function} [customizer] The function to customize comparisons.
     * @returns {boolean} Returns `true` if `object` is a match, else `false`.
     */
    function baseIsMatch(object, source, matchData, customizer) {
      var index = matchData.length,
          length = index,
          noCustomizer = !customizer;
    
      if (object == null) {
        return !length;
      }
      object = Object(object);
      while (index--) {
        var data = matchData[index];
        if ((noCustomizer && data[2])
              ? data[1] !== object[data[0]]
              : !(data[0] in object)
            ) {
          return false;
        }
      }
      while (++index < length) {
        data = matchData[index];
        var key = data[0],
            objValue = object[key],
            srcValue = data[1];
    
        if (noCustomizer && data[2]) {
          if (objValue === undefined && !(key in object)) {
            return false;
          }
        } else {
          var stack = new Stack;
          if (customizer) {
            var result = customizer(objValue, srcValue, key, object, source, stack);
          }
          if (!(result === undefined
                ? baseIsEqual(srcValue, objValue, COMPARE_PARTIAL_FLAG | COMPARE_UNORDERED_FLAG, customizer, stack)
                : result
              )) {
            return false;
          }
        }
      }
      return true;
    }
    
    module.exports = baseIsMatch;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseIsNative.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_baseIsNative.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isFunction = __webpack_require__(/*! ./isFunction */ "./node_modules/lodash/isFunction.js"),
        isMasked = __webpack_require__(/*! ./_isMasked */ "./node_modules/lodash/_isMasked.js"),
        isObject = __webpack_require__(/*! ./isObject */ "./node_modules/lodash/isObject.js"),
        toSource = __webpack_require__(/*! ./_toSource */ "./node_modules/lodash/_toSource.js");
    
    /**
     * Used to match `RegExp`
     * [syntax characters](http://ecma-international.org/ecma-262/7.0/#sec-patterns).
     */
    var reRegExpChar = /[\\^$.*+?()[\]{}|]/g;
    
    /** Used to detect host constructors (Safari). */
    var reIsHostCtor = /^\[object .+?Constructor\]$/;
    
    /** Used for built-in method references. */
    var funcProto = Function.prototype,
        objectProto = Object.prototype;
    
    /** Used to resolve the decompiled source of functions. */
    var funcToString = funcProto.toString;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /** Used to detect if a method is native. */
    var reIsNative = RegExp('^' +
      funcToString.call(hasOwnProperty).replace(reRegExpChar, '\\$&')
      .replace(/hasOwnProperty|(function).*?(?=\\\()| for .+?(?=\\\])/g, '$1.*?') + '$'
    );
    
    /**
     * The base implementation of `_.isNative` without bad shim checks.
     *
     * @private
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a native function,
     *  else `false`.
     */
    function baseIsNative(value) {
      if (!isObject(value) || isMasked(value)) {
        return false;
      }
      var pattern = isFunction(value) ? reIsNative : reIsHostCtor;
      return pattern.test(toSource(value));
    }
    
    module.exports = baseIsNative;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseIsTypedArray.js":
    /*!**************************************************!*\
      !*** ./node_modules/lodash/_baseIsTypedArray.js ***!
      \**************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ "./node_modules/lodash/_baseGetTag.js"),
        isLength = __webpack_require__(/*! ./isLength */ "./node_modules/lodash/isLength.js"),
        isObjectLike = __webpack_require__(/*! ./isObjectLike */ "./node_modules/lodash/isObjectLike.js");
    
    /** `Object#toString` result references. */
    var argsTag = '[object Arguments]',
        arrayTag = '[object Array]',
        boolTag = '[object Boolean]',
        dateTag = '[object Date]',
        errorTag = '[object Error]',
        funcTag = '[object Function]',
        mapTag = '[object Map]',
        numberTag = '[object Number]',
        objectTag = '[object Object]',
        regexpTag = '[object RegExp]',
        setTag = '[object Set]',
        stringTag = '[object String]',
        weakMapTag = '[object WeakMap]';
    
    var arrayBufferTag = '[object ArrayBuffer]',
        dataViewTag = '[object DataView]',
        float32Tag = '[object Float32Array]',
        float64Tag = '[object Float64Array]',
        int8Tag = '[object Int8Array]',
        int16Tag = '[object Int16Array]',
        int32Tag = '[object Int32Array]',
        uint8Tag = '[object Uint8Array]',
        uint8ClampedTag = '[object Uint8ClampedArray]',
        uint16Tag = '[object Uint16Array]',
        uint32Tag = '[object Uint32Array]';
    
    /** Used to identify `toStringTag` values of typed arrays. */
    var typedArrayTags = {};
    typedArrayTags[float32Tag] = typedArrayTags[float64Tag] =
    typedArrayTags[int8Tag] = typedArrayTags[int16Tag] =
    typedArrayTags[int32Tag] = typedArrayTags[uint8Tag] =
    typedArrayTags[uint8ClampedTag] = typedArrayTags[uint16Tag] =
    typedArrayTags[uint32Tag] = true;
    typedArrayTags[argsTag] = typedArrayTags[arrayTag] =
    typedArrayTags[arrayBufferTag] = typedArrayTags[boolTag] =
    typedArrayTags[dataViewTag] = typedArrayTags[dateTag] =
    typedArrayTags[errorTag] = typedArrayTags[funcTag] =
    typedArrayTags[mapTag] = typedArrayTags[numberTag] =
    typedArrayTags[objectTag] = typedArrayTags[regexpTag] =
    typedArrayTags[setTag] = typedArrayTags[stringTag] =
    typedArrayTags[weakMapTag] = false;
    
    /**
     * The base implementation of `_.isTypedArray` without Node.js optimizations.
     *
     * @private
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a typed array, else `false`.
     */
    function baseIsTypedArray(value) {
      return isObjectLike(value) &&
        isLength(value.length) && !!typedArrayTags[baseGetTag(value)];
    }
    
    module.exports = baseIsTypedArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseIteratee.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_baseIteratee.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseMatches = __webpack_require__(/*! ./_baseMatches */ "./node_modules/lodash/_baseMatches.js"),
        baseMatchesProperty = __webpack_require__(/*! ./_baseMatchesProperty */ "./node_modules/lodash/_baseMatchesProperty.js"),
        identity = __webpack_require__(/*! ./identity */ "./node_modules/lodash/identity.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        property = __webpack_require__(/*! ./property */ "./node_modules/lodash/property.js");
    
    /**
     * The base implementation of `_.iteratee`.
     *
     * @private
     * @param {*} [value=_.identity] The value to convert to an iteratee.
     * @returns {Function} Returns the iteratee.
     */
    function baseIteratee(value) {
      // Don't store the `typeof` result in a variable to avoid a JIT bug in Safari 9.
      // See https://bugs.webkit.org/show_bug.cgi?id=156034 for more details.
      if (typeof value == 'function') {
        return value;
      }
      if (value == null) {
        return identity;
      }
      if (typeof value == 'object') {
        return isArray(value)
          ? baseMatchesProperty(value[0], value[1])
          : baseMatches(value);
      }
      return property(value);
    }
    
    module.exports = baseIteratee;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseKeys.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_baseKeys.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isPrototype = __webpack_require__(/*! ./_isPrototype */ "./node_modules/lodash/_isPrototype.js"),
        nativeKeys = __webpack_require__(/*! ./_nativeKeys */ "./node_modules/lodash/_nativeKeys.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * The base implementation of `_.keys` which doesn't treat sparse arrays as dense.
     *
     * @private
     * @param {Object} object The object to query.
     * @returns {Array} Returns the array of property names.
     */
    function baseKeys(object) {
      if (!isPrototype(object)) {
        return nativeKeys(object);
      }
      var result = [];
      for (var key in Object(object)) {
        if (hasOwnProperty.call(object, key) && key != 'constructor') {
          result.push(key);
        }
      }
      return result;
    }
    
    module.exports = baseKeys;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseKeysIn.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_baseKeysIn.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isObject = __webpack_require__(/*! ./isObject */ "./node_modules/lodash/isObject.js"),
        isPrototype = __webpack_require__(/*! ./_isPrototype */ "./node_modules/lodash/_isPrototype.js"),
        nativeKeysIn = __webpack_require__(/*! ./_nativeKeysIn */ "./node_modules/lodash/_nativeKeysIn.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * The base implementation of `_.keysIn` which doesn't treat sparse arrays as dense.
     *
     * @private
     * @param {Object} object The object to query.
     * @returns {Array} Returns the array of property names.
     */
    function baseKeysIn(object) {
      if (!isObject(object)) {
        return nativeKeysIn(object);
      }
      var isProto = isPrototype(object),
          result = [];
    
      for (var key in object) {
        if (!(key == 'constructor' && (isProto || !hasOwnProperty.call(object, key)))) {
          result.push(key);
        }
      }
      return result;
    }
    
    module.exports = baseKeysIn;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseMatches.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_baseMatches.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseIsMatch = __webpack_require__(/*! ./_baseIsMatch */ "./node_modules/lodash/_baseIsMatch.js"),
        getMatchData = __webpack_require__(/*! ./_getMatchData */ "./node_modules/lodash/_getMatchData.js"),
        matchesStrictComparable = __webpack_require__(/*! ./_matchesStrictComparable */ "./node_modules/lodash/_matchesStrictComparable.js");
    
    /**
     * The base implementation of `_.matches` which doesn't clone `source`.
     *
     * @private
     * @param {Object} source The object of property values to match.
     * @returns {Function} Returns the new spec function.
     */
    function baseMatches(source) {
      var matchData = getMatchData(source);
      if (matchData.length == 1 && matchData[0][2]) {
        return matchesStrictComparable(matchData[0][0], matchData[0][1]);
      }
      return function(object) {
        return object === source || baseIsMatch(object, source, matchData);
      };
    }
    
    module.exports = baseMatches;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseMatchesProperty.js":
    /*!*****************************************************!*\
      !*** ./node_modules/lodash/_baseMatchesProperty.js ***!
      \*****************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseIsEqual = __webpack_require__(/*! ./_baseIsEqual */ "./node_modules/lodash/_baseIsEqual.js"),
        get = __webpack_require__(/*! ./get */ "./node_modules/lodash/get.js"),
        hasIn = __webpack_require__(/*! ./hasIn */ "./node_modules/lodash/hasIn.js"),
        isKey = __webpack_require__(/*! ./_isKey */ "./node_modules/lodash/_isKey.js"),
        isStrictComparable = __webpack_require__(/*! ./_isStrictComparable */ "./node_modules/lodash/_isStrictComparable.js"),
        matchesStrictComparable = __webpack_require__(/*! ./_matchesStrictComparable */ "./node_modules/lodash/_matchesStrictComparable.js"),
        toKey = __webpack_require__(/*! ./_toKey */ "./node_modules/lodash/_toKey.js");
    
    /** Used to compose bitmasks for value comparisons. */
    var COMPARE_PARTIAL_FLAG = 1,
        COMPARE_UNORDERED_FLAG = 2;
    
    /**
     * The base implementation of `_.matchesProperty` which doesn't clone `srcValue`.
     *
     * @private
     * @param {string} path The path of the property to get.
     * @param {*} srcValue The value to match.
     * @returns {Function} Returns the new spec function.
     */
    function baseMatchesProperty(path, srcValue) {
      if (isKey(path) && isStrictComparable(srcValue)) {
        return matchesStrictComparable(toKey(path), srcValue);
      }
      return function(object) {
        var objValue = get(object, path);
        return (objValue === undefined && objValue === srcValue)
          ? hasIn(object, path)
          : baseIsEqual(srcValue, objValue, COMPARE_PARTIAL_FLAG | COMPARE_UNORDERED_FLAG);
      };
    }
    
    module.exports = baseMatchesProperty;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseProperty.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_baseProperty.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * The base implementation of `_.property` without support for deep paths.
     *
     * @private
     * @param {string} key The key of the property to get.
     * @returns {Function} Returns the new accessor function.
     */
    function baseProperty(key) {
      return function(object) {
        return object == null ? undefined : object[key];
      };
    }
    
    module.exports = baseProperty;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_basePropertyDeep.js":
    /*!**************************************************!*\
      !*** ./node_modules/lodash/_basePropertyDeep.js ***!
      \**************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGet = __webpack_require__(/*! ./_baseGet */ "./node_modules/lodash/_baseGet.js");
    
    /**
     * A specialized version of `baseProperty` which supports deep paths.
     *
     * @private
     * @param {Array|string} path The path of the property to get.
     * @returns {Function} Returns the new accessor function.
     */
    function basePropertyDeep(path) {
      return function(object) {
        return baseGet(object, path);
      };
    }
    
    module.exports = basePropertyDeep;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_basePropertyOf.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_basePropertyOf.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * The base implementation of `_.propertyOf` without support for deep paths.
     *
     * @private
     * @param {Object} object The object to query.
     * @returns {Function} Returns the new accessor function.
     */
    function basePropertyOf(object) {
      return function(key) {
        return object == null ? undefined : object[key];
      };
    }
    
    module.exports = basePropertyOf;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseRest.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_baseRest.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var identity = __webpack_require__(/*! ./identity */ "./node_modules/lodash/identity.js"),
        overRest = __webpack_require__(/*! ./_overRest */ "./node_modules/lodash/_overRest.js"),
        setToString = __webpack_require__(/*! ./_setToString */ "./node_modules/lodash/_setToString.js");
    
    /**
     * The base implementation of `_.rest` which doesn't validate or coerce arguments.
     *
     * @private
     * @param {Function} func The function to apply a rest parameter to.
     * @param {number} [start=func.length-1] The start position of the rest parameter.
     * @returns {Function} Returns the new function.
     */
    function baseRest(func, start) {
      return setToString(overRest(func, start, identity), func + '');
    }
    
    module.exports = baseRest;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseSetToString.js":
    /*!*************************************************!*\
      !*** ./node_modules/lodash/_baseSetToString.js ***!
      \*************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var constant = __webpack_require__(/*! ./constant */ "./node_modules/lodash/constant.js"),
        defineProperty = __webpack_require__(/*! ./_defineProperty */ "./node_modules/lodash/_defineProperty.js"),
        identity = __webpack_require__(/*! ./identity */ "./node_modules/lodash/identity.js");
    
    /**
     * The base implementation of `setToString` without support for hot loop shorting.
     *
     * @private
     * @param {Function} func The function to modify.
     * @param {Function} string The `toString` result.
     * @returns {Function} Returns `func`.
     */
    var baseSetToString = !defineProperty ? identity : function(func, string) {
      return defineProperty(func, 'toString', {
        'configurable': true,
        'enumerable': false,
        'value': constant(string),
        'writable': true
      });
    };
    
    module.exports = baseSetToString;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseSlice.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_baseSlice.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * The base implementation of `_.slice` without an iteratee call guard.
     *
     * @private
     * @param {Array} array The array to slice.
     * @param {number} [start=0] The start position.
     * @param {number} [end=array.length] The end position.
     * @returns {Array} Returns the slice of `array`.
     */
    function baseSlice(array, start, end) {
      var index = -1,
          length = array.length;
    
      if (start < 0) {
        start = -start > length ? 0 : (length + start);
      }
      end = end > length ? length : end;
      if (end < 0) {
        end += length;
      }
      length = start > end ? 0 : ((end - start) >>> 0);
      start >>>= 0;
    
      var result = Array(length);
      while (++index < length) {
        result[index] = array[index + start];
      }
      return result;
    }
    
    module.exports = baseSlice;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseTimes.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_baseTimes.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * The base implementation of `_.times` without support for iteratee shorthands
     * or max array length checks.
     *
     * @private
     * @param {number} n The number of times to invoke `iteratee`.
     * @param {Function} iteratee The function invoked per iteration.
     * @returns {Array} Returns the array of results.
     */
    function baseTimes(n, iteratee) {
      var index = -1,
          result = Array(n);
    
      while (++index < n) {
        result[index] = iteratee(index);
      }
      return result;
    }
    
    module.exports = baseTimes;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseToString.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_baseToString.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var Symbol = __webpack_require__(/*! ./_Symbol */ "./node_modules/lodash/_Symbol.js"),
        arrayMap = __webpack_require__(/*! ./_arrayMap */ "./node_modules/lodash/_arrayMap.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        isSymbol = __webpack_require__(/*! ./isSymbol */ "./node_modules/lodash/isSymbol.js");
    
    /** Used as references for various `Number` constants. */
    var INFINITY = 1 / 0;
    
    /** Used to convert symbols to primitives and strings. */
    var symbolProto = Symbol ? Symbol.prototype : undefined,
        symbolToString = symbolProto ? symbolProto.toString : undefined;
    
    /**
     * The base implementation of `_.toString` which doesn't convert nullish
     * values to empty strings.
     *
     * @private
     * @param {*} value The value to process.
     * @returns {string} Returns the string.
     */
    function baseToString(value) {
      // Exit early for strings to avoid a performance hit in some environments.
      if (typeof value == 'string') {
        return value;
      }
      if (isArray(value)) {
        // Recursively convert values (susceptible to call stack limits).
        return arrayMap(value, baseToString) + '';
      }
      if (isSymbol(value)) {
        return symbolToString ? symbolToString.call(value) : '';
      }
      var result = (value + '');
      return (result == '0' && (1 / value) == -INFINITY) ? '-0' : result;
    }
    
    module.exports = baseToString;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_baseUnary.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_baseUnary.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * The base implementation of `_.unary` without support for storing metadata.
     *
     * @private
     * @param {Function} func The function to cap arguments for.
     * @returns {Function} Returns the new capped function.
     */
    function baseUnary(func) {
      return function(value) {
        return func(value);
      };
    }
    
    module.exports = baseUnary;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_cacheHas.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_cacheHas.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if a `cache` value for `key` exists.
     *
     * @private
     * @param {Object} cache The cache to query.
     * @param {string} key The key of the entry to check.
     * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
     */
    function cacheHas(cache, key) {
      return cache.has(key);
    }
    
    module.exports = cacheHas;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_castFunction.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_castFunction.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var identity = __webpack_require__(/*! ./identity */ "./node_modules/lodash/identity.js");
    
    /**
     * Casts `value` to `identity` if it's not a function.
     *
     * @private
     * @param {*} value The value to inspect.
     * @returns {Function} Returns cast function.
     */
    function castFunction(value) {
      return typeof value == 'function' ? value : identity;
    }
    
    module.exports = castFunction;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_castPath.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_castPath.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        isKey = __webpack_require__(/*! ./_isKey */ "./node_modules/lodash/_isKey.js"),
        stringToPath = __webpack_require__(/*! ./_stringToPath */ "./node_modules/lodash/_stringToPath.js"),
        toString = __webpack_require__(/*! ./toString */ "./node_modules/lodash/toString.js");
    
    /**
     * Casts `value` to a path array if it's not one.
     *
     * @private
     * @param {*} value The value to inspect.
     * @param {Object} [object] The object to query keys on.
     * @returns {Array} Returns the cast property path array.
     */
    function castPath(value, object) {
      if (isArray(value)) {
        return value;
      }
      return isKey(value, object) ? [value] : stringToPath(toString(value));
    }
    
    module.exports = castPath;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_castSlice.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_castSlice.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseSlice = __webpack_require__(/*! ./_baseSlice */ "./node_modules/lodash/_baseSlice.js");
    
    /**
     * Casts `array` to a slice if it's needed.
     *
     * @private
     * @param {Array} array The array to inspect.
     * @param {number} start The start position.
     * @param {number} [end=array.length] The end position.
     * @returns {Array} Returns the cast slice.
     */
    function castSlice(array, start, end) {
      var length = array.length;
      end = end === undefined ? length : end;
      return (!start && end >= length) ? array : baseSlice(array, start, end);
    }
    
    module.exports = castSlice;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_coreJsData.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_coreJsData.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js");
    
    /** Used to detect overreaching core-js shims. */
    var coreJsData = root['__core-js_shared__'];
    
    module.exports = coreJsData;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_createBaseEach.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_createBaseEach.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isArrayLike = __webpack_require__(/*! ./isArrayLike */ "./node_modules/lodash/isArrayLike.js");
    
    /**
     * Creates a `baseEach` or `baseEachRight` function.
     *
     * @private
     * @param {Function} eachFunc The function to iterate over a collection.
     * @param {boolean} [fromRight] Specify iterating from right to left.
     * @returns {Function} Returns the new base function.
     */
    function createBaseEach(eachFunc, fromRight) {
      return function(collection, iteratee) {
        if (collection == null) {
          return collection;
        }
        if (!isArrayLike(collection)) {
          return eachFunc(collection, iteratee);
        }
        var length = collection.length,
            index = fromRight ? length : -1,
            iterable = Object(collection);
    
        while ((fromRight ? index-- : ++index < length)) {
          if (iteratee(iterable[index], index, iterable) === false) {
            break;
          }
        }
        return collection;
      };
    }
    
    module.exports = createBaseEach;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_createBaseFor.js":
    /*!***********************************************!*\
      !*** ./node_modules/lodash/_createBaseFor.js ***!
      \***********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Creates a base function for methods like `_.forIn` and `_.forOwn`.
     *
     * @private
     * @param {boolean} [fromRight] Specify iterating from right to left.
     * @returns {Function} Returns the new base function.
     */
    function createBaseFor(fromRight) {
      return function(object, iteratee, keysFunc) {
        var index = -1,
            iterable = Object(object),
            props = keysFunc(object),
            length = props.length;
    
        while (length--) {
          var key = props[fromRight ? length : ++index];
          if (iteratee(iterable[key], key, iterable) === false) {
            break;
          }
        }
        return object;
      };
    }
    
    module.exports = createBaseFor;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_createCaseFirst.js":
    /*!*************************************************!*\
      !*** ./node_modules/lodash/_createCaseFirst.js ***!
      \*************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var castSlice = __webpack_require__(/*! ./_castSlice */ "./node_modules/lodash/_castSlice.js"),
        hasUnicode = __webpack_require__(/*! ./_hasUnicode */ "./node_modules/lodash/_hasUnicode.js"),
        stringToArray = __webpack_require__(/*! ./_stringToArray */ "./node_modules/lodash/_stringToArray.js"),
        toString = __webpack_require__(/*! ./toString */ "./node_modules/lodash/toString.js");
    
    /**
     * Creates a function like `_.lowerFirst`.
     *
     * @private
     * @param {string} methodName The name of the `String` case method to use.
     * @returns {Function} Returns the new case function.
     */
    function createCaseFirst(methodName) {
      return function(string) {
        string = toString(string);
    
        var strSymbols = hasUnicode(string)
          ? stringToArray(string)
          : undefined;
    
        var chr = strSymbols
          ? strSymbols[0]
          : string.charAt(0);
    
        var trailing = strSymbols
          ? castSlice(strSymbols, 1).join('')
          : string.slice(1);
    
        return chr[methodName]() + trailing;
      };
    }
    
    module.exports = createCaseFirst;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_createCompounder.js":
    /*!**************************************************!*\
      !*** ./node_modules/lodash/_createCompounder.js ***!
      \**************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var arrayReduce = __webpack_require__(/*! ./_arrayReduce */ "./node_modules/lodash/_arrayReduce.js"),
        deburr = __webpack_require__(/*! ./deburr */ "./node_modules/lodash/deburr.js"),
        words = __webpack_require__(/*! ./words */ "./node_modules/lodash/words.js");
    
    /** Used to compose unicode capture groups. */
    var rsApos = "['\u2019]";
    
    /** Used to match apostrophes. */
    var reApos = RegExp(rsApos, 'g');
    
    /**
     * Creates a function like `_.camelCase`.
     *
     * @private
     * @param {Function} callback The function to combine each word.
     * @returns {Function} Returns the new compounder function.
     */
    function createCompounder(callback) {
      return function(string) {
        return arrayReduce(words(deburr(string).replace(reApos, '')), callback, '');
      };
    }
    
    module.exports = createCompounder;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_deburrLetter.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_deburrLetter.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var basePropertyOf = __webpack_require__(/*! ./_basePropertyOf */ "./node_modules/lodash/_basePropertyOf.js");
    
    /** Used to map Latin Unicode letters to basic Latin letters. */
    var deburredLetters = {
      // Latin-1 Supplement block.
      '\xc0': 'A',  '\xc1': 'A', '\xc2': 'A', '\xc3': 'A', '\xc4': 'A', '\xc5': 'A',
      '\xe0': 'a',  '\xe1': 'a', '\xe2': 'a', '\xe3': 'a', '\xe4': 'a', '\xe5': 'a',
      '\xc7': 'C',  '\xe7': 'c',
      '\xd0': 'D',  '\xf0': 'd',
      '\xc8': 'E',  '\xc9': 'E', '\xca': 'E', '\xcb': 'E',
      '\xe8': 'e',  '\xe9': 'e', '\xea': 'e', '\xeb': 'e',
      '\xcc': 'I',  '\xcd': 'I', '\xce': 'I', '\xcf': 'I',
      '\xec': 'i',  '\xed': 'i', '\xee': 'i', '\xef': 'i',
      '\xd1': 'N',  '\xf1': 'n',
      '\xd2': 'O',  '\xd3': 'O', '\xd4': 'O', '\xd5': 'O', '\xd6': 'O', '\xd8': 'O',
      '\xf2': 'o',  '\xf3': 'o', '\xf4': 'o', '\xf5': 'o', '\xf6': 'o', '\xf8': 'o',
      '\xd9': 'U',  '\xda': 'U', '\xdb': 'U', '\xdc': 'U',
      '\xf9': 'u',  '\xfa': 'u', '\xfb': 'u', '\xfc': 'u',
      '\xdd': 'Y',  '\xfd': 'y', '\xff': 'y',
      '\xc6': 'Ae', '\xe6': 'ae',
      '\xde': 'Th', '\xfe': 'th',
      '\xdf': 'ss',
      // Latin Extended-A block.
      '\u0100': 'A',  '\u0102': 'A', '\u0104': 'A',
      '\u0101': 'a',  '\u0103': 'a', '\u0105': 'a',
      '\u0106': 'C',  '\u0108': 'C', '\u010a': 'C', '\u010c': 'C',
      '\u0107': 'c',  '\u0109': 'c', '\u010b': 'c', '\u010d': 'c',
      '\u010e': 'D',  '\u0110': 'D', '\u010f': 'd', '\u0111': 'd',
      '\u0112': 'E',  '\u0114': 'E', '\u0116': 'E', '\u0118': 'E', '\u011a': 'E',
      '\u0113': 'e',  '\u0115': 'e', '\u0117': 'e', '\u0119': 'e', '\u011b': 'e',
      '\u011c': 'G',  '\u011e': 'G', '\u0120': 'G', '\u0122': 'G',
      '\u011d': 'g',  '\u011f': 'g', '\u0121': 'g', '\u0123': 'g',
      '\u0124': 'H',  '\u0126': 'H', '\u0125': 'h', '\u0127': 'h',
      '\u0128': 'I',  '\u012a': 'I', '\u012c': 'I', '\u012e': 'I', '\u0130': 'I',
      '\u0129': 'i',  '\u012b': 'i', '\u012d': 'i', '\u012f': 'i', '\u0131': 'i',
      '\u0134': 'J',  '\u0135': 'j',
      '\u0136': 'K',  '\u0137': 'k', '\u0138': 'k',
      '\u0139': 'L',  '\u013b': 'L', '\u013d': 'L', '\u013f': 'L', '\u0141': 'L',
      '\u013a': 'l',  '\u013c': 'l', '\u013e': 'l', '\u0140': 'l', '\u0142': 'l',
      '\u0143': 'N',  '\u0145': 'N', '\u0147': 'N', '\u014a': 'N',
      '\u0144': 'n',  '\u0146': 'n', '\u0148': 'n', '\u014b': 'n',
      '\u014c': 'O',  '\u014e': 'O', '\u0150': 'O',
      '\u014d': 'o',  '\u014f': 'o', '\u0151': 'o',
      '\u0154': 'R',  '\u0156': 'R', '\u0158': 'R',
      '\u0155': 'r',  '\u0157': 'r', '\u0159': 'r',
      '\u015a': 'S',  '\u015c': 'S', '\u015e': 'S', '\u0160': 'S',
      '\u015b': 's',  '\u015d': 's', '\u015f': 's', '\u0161': 's',
      '\u0162': 'T',  '\u0164': 'T', '\u0166': 'T',
      '\u0163': 't',  '\u0165': 't', '\u0167': 't',
      '\u0168': 'U',  '\u016a': 'U', '\u016c': 'U', '\u016e': 'U', '\u0170': 'U', '\u0172': 'U',
      '\u0169': 'u',  '\u016b': 'u', '\u016d': 'u', '\u016f': 'u', '\u0171': 'u', '\u0173': 'u',
      '\u0174': 'W',  '\u0175': 'w',
      '\u0176': 'Y',  '\u0177': 'y', '\u0178': 'Y',
      '\u0179': 'Z',  '\u017b': 'Z', '\u017d': 'Z',
      '\u017a': 'z',  '\u017c': 'z', '\u017e': 'z',
      '\u0132': 'IJ', '\u0133': 'ij',
      '\u0152': 'Oe', '\u0153': 'oe',
      '\u0149': "'n", '\u017f': 's'
    };
    
    /**
     * Used by `_.deburr` to convert Latin-1 Supplement and Latin Extended-A
     * letters to basic Latin letters.
     *
     * @private
     * @param {string} letter The matched letter to deburr.
     * @returns {string} Returns the deburred letter.
     */
    var deburrLetter = basePropertyOf(deburredLetters);
    
    module.exports = deburrLetter;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_defineProperty.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_defineProperty.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getNative = __webpack_require__(/*! ./_getNative */ "./node_modules/lodash/_getNative.js");
    
    var defineProperty = (function() {
      try {
        var func = getNative(Object, 'defineProperty');
        func({}, '', {});
        return func;
      } catch (e) {}
    }());
    
    module.exports = defineProperty;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_equalArrays.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_equalArrays.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var SetCache = __webpack_require__(/*! ./_SetCache */ "./node_modules/lodash/_SetCache.js"),
        arraySome = __webpack_require__(/*! ./_arraySome */ "./node_modules/lodash/_arraySome.js"),
        cacheHas = __webpack_require__(/*! ./_cacheHas */ "./node_modules/lodash/_cacheHas.js");
    
    /** Used to compose bitmasks for value comparisons. */
    var COMPARE_PARTIAL_FLAG = 1,
        COMPARE_UNORDERED_FLAG = 2;
    
    /**
     * A specialized version of `baseIsEqualDeep` for arrays with support for
     * partial deep comparisons.
     *
     * @private
     * @param {Array} array The array to compare.
     * @param {Array} other The other array to compare.
     * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
     * @param {Function} customizer The function to customize comparisons.
     * @param {Function} equalFunc The function to determine equivalents of values.
     * @param {Object} stack Tracks traversed `array` and `other` objects.
     * @returns {boolean} Returns `true` if the arrays are equivalent, else `false`.
     */
    function equalArrays(array, other, bitmask, customizer, equalFunc, stack) {
      var isPartial = bitmask & COMPARE_PARTIAL_FLAG,
          arrLength = array.length,
          othLength = other.length;
    
      if (arrLength != othLength && !(isPartial && othLength > arrLength)) {
        return false;
      }
      // Check that cyclic values are equal.
      var arrStacked = stack.get(array);
      var othStacked = stack.get(other);
      if (arrStacked && othStacked) {
        return arrStacked == other && othStacked == array;
      }
      var index = -1,
          result = true,
          seen = (bitmask & COMPARE_UNORDERED_FLAG) ? new SetCache : undefined;
    
      stack.set(array, other);
      stack.set(other, array);
    
      // Ignore non-index properties.
      while (++index < arrLength) {
        var arrValue = array[index],
            othValue = other[index];
    
        if (customizer) {
          var compared = isPartial
            ? customizer(othValue, arrValue, index, other, array, stack)
            : customizer(arrValue, othValue, index, array, other, stack);
        }
        if (compared !== undefined) {
          if (compared) {
            continue;
          }
          result = false;
          break;
        }
        // Recursively compare arrays (susceptible to call stack limits).
        if (seen) {
          if (!arraySome(other, function(othValue, othIndex) {
                if (!cacheHas(seen, othIndex) &&
                    (arrValue === othValue || equalFunc(arrValue, othValue, bitmask, customizer, stack))) {
                  return seen.push(othIndex);
                }
              })) {
            result = false;
            break;
          }
        } else if (!(
              arrValue === othValue ||
                equalFunc(arrValue, othValue, bitmask, customizer, stack)
            )) {
          result = false;
          break;
        }
      }
      stack['delete'](array);
      stack['delete'](other);
      return result;
    }
    
    module.exports = equalArrays;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_equalByTag.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_equalByTag.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var Symbol = __webpack_require__(/*! ./_Symbol */ "./node_modules/lodash/_Symbol.js"),
        Uint8Array = __webpack_require__(/*! ./_Uint8Array */ "./node_modules/lodash/_Uint8Array.js"),
        eq = __webpack_require__(/*! ./eq */ "./node_modules/lodash/eq.js"),
        equalArrays = __webpack_require__(/*! ./_equalArrays */ "./node_modules/lodash/_equalArrays.js"),
        mapToArray = __webpack_require__(/*! ./_mapToArray */ "./node_modules/lodash/_mapToArray.js"),
        setToArray = __webpack_require__(/*! ./_setToArray */ "./node_modules/lodash/_setToArray.js");
    
    /** Used to compose bitmasks for value comparisons. */
    var COMPARE_PARTIAL_FLAG = 1,
        COMPARE_UNORDERED_FLAG = 2;
    
    /** `Object#toString` result references. */
    var boolTag = '[object Boolean]',
        dateTag = '[object Date]',
        errorTag = '[object Error]',
        mapTag = '[object Map]',
        numberTag = '[object Number]',
        regexpTag = '[object RegExp]',
        setTag = '[object Set]',
        stringTag = '[object String]',
        symbolTag = '[object Symbol]';
    
    var arrayBufferTag = '[object ArrayBuffer]',
        dataViewTag = '[object DataView]';
    
    /** Used to convert symbols to primitives and strings. */
    var symbolProto = Symbol ? Symbol.prototype : undefined,
        symbolValueOf = symbolProto ? symbolProto.valueOf : undefined;
    
    /**
     * A specialized version of `baseIsEqualDeep` for comparing objects of
     * the same `toStringTag`.
     *
     * **Note:** This function only supports comparing values with tags of
     * `Boolean`, `Date`, `Error`, `Number`, `RegExp`, or `String`.
     *
     * @private
     * @param {Object} object The object to compare.
     * @param {Object} other The other object to compare.
     * @param {string} tag The `toStringTag` of the objects to compare.
     * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
     * @param {Function} customizer The function to customize comparisons.
     * @param {Function} equalFunc The function to determine equivalents of values.
     * @param {Object} stack Tracks traversed `object` and `other` objects.
     * @returns {boolean} Returns `true` if the objects are equivalent, else `false`.
     */
    function equalByTag(object, other, tag, bitmask, customizer, equalFunc, stack) {
      switch (tag) {
        case dataViewTag:
          if ((object.byteLength != other.byteLength) ||
              (object.byteOffset != other.byteOffset)) {
            return false;
          }
          object = object.buffer;
          other = other.buffer;
    
        case arrayBufferTag:
          if ((object.byteLength != other.byteLength) ||
              !equalFunc(new Uint8Array(object), new Uint8Array(other))) {
            return false;
          }
          return true;
    
        case boolTag:
        case dateTag:
        case numberTag:
          // Coerce booleans to `1` or `0` and dates to milliseconds.
          // Invalid dates are coerced to `NaN`.
          return eq(+object, +other);
    
        case errorTag:
          return object.name == other.name && object.message == other.message;
    
        case regexpTag:
        case stringTag:
          // Coerce regexes to strings and treat strings, primitives and objects,
          // as equal. See http://www.ecma-international.org/ecma-262/7.0/#sec-regexp.prototype.tostring
          // for more details.
          return object == (other + '');
    
        case mapTag:
          var convert = mapToArray;
    
        case setTag:
          var isPartial = bitmask & COMPARE_PARTIAL_FLAG;
          convert || (convert = setToArray);
    
          if (object.size != other.size && !isPartial) {
            return false;
          }
          // Assume cyclic values are equal.
          var stacked = stack.get(object);
          if (stacked) {
            return stacked == other;
          }
          bitmask |= COMPARE_UNORDERED_FLAG;
    
          // Recursively compare objects (susceptible to call stack limits).
          stack.set(object, other);
          var result = equalArrays(convert(object), convert(other), bitmask, customizer, equalFunc, stack);
          stack['delete'](object);
          return result;
    
        case symbolTag:
          if (symbolValueOf) {
            return symbolValueOf.call(object) == symbolValueOf.call(other);
          }
      }
      return false;
    }
    
    module.exports = equalByTag;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_equalObjects.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_equalObjects.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getAllKeys = __webpack_require__(/*! ./_getAllKeys */ "./node_modules/lodash/_getAllKeys.js");
    
    /** Used to compose bitmasks for value comparisons. */
    var COMPARE_PARTIAL_FLAG = 1;
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * A specialized version of `baseIsEqualDeep` for objects with support for
     * partial deep comparisons.
     *
     * @private
     * @param {Object} object The object to compare.
     * @param {Object} other The other object to compare.
     * @param {number} bitmask The bitmask flags. See `baseIsEqual` for more details.
     * @param {Function} customizer The function to customize comparisons.
     * @param {Function} equalFunc The function to determine equivalents of values.
     * @param {Object} stack Tracks traversed `object` and `other` objects.
     * @returns {boolean} Returns `true` if the objects are equivalent, else `false`.
     */
    function equalObjects(object, other, bitmask, customizer, equalFunc, stack) {
      var isPartial = bitmask & COMPARE_PARTIAL_FLAG,
          objProps = getAllKeys(object),
          objLength = objProps.length,
          othProps = getAllKeys(other),
          othLength = othProps.length;
    
      if (objLength != othLength && !isPartial) {
        return false;
      }
      var index = objLength;
      while (index--) {
        var key = objProps[index];
        if (!(isPartial ? key in other : hasOwnProperty.call(other, key))) {
          return false;
        }
      }
      // Check that cyclic values are equal.
      var objStacked = stack.get(object);
      var othStacked = stack.get(other);
      if (objStacked && othStacked) {
        return objStacked == other && othStacked == object;
      }
      var result = true;
      stack.set(object, other);
      stack.set(other, object);
    
      var skipCtor = isPartial;
      while (++index < objLength) {
        key = objProps[index];
        var objValue = object[key],
            othValue = other[key];
    
        if (customizer) {
          var compared = isPartial
            ? customizer(othValue, objValue, key, other, object, stack)
            : customizer(objValue, othValue, key, object, other, stack);
        }
        // Recursively compare objects (susceptible to call stack limits).
        if (!(compared === undefined
              ? (objValue === othValue || equalFunc(objValue, othValue, bitmask, customizer, stack))
              : compared
            )) {
          result = false;
          break;
        }
        skipCtor || (skipCtor = key == 'constructor');
      }
      if (result && !skipCtor) {
        var objCtor = object.constructor,
            othCtor = other.constructor;
    
        // Non `Object` object instances with different constructors are not equal.
        if (objCtor != othCtor &&
            ('constructor' in object && 'constructor' in other) &&
            !(typeof objCtor == 'function' && objCtor instanceof objCtor &&
              typeof othCtor == 'function' && othCtor instanceof othCtor)) {
          result = false;
        }
      }
      stack['delete'](object);
      stack['delete'](other);
      return result;
    }
    
    module.exports = equalObjects;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_freeGlobal.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_freeGlobal.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    /* WEBPACK VAR INJECTION */(function(global) {/** Detect free variable `global` from Node.js. */
    var freeGlobal = typeof global == 'object' && global && global.Object === Object && global;
    
    module.exports = freeGlobal;
    
    /* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./../webpack/buildin/global.js */ "./node_modules/webpack/buildin/global.js")))
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getAllKeys.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_getAllKeys.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGetAllKeys = __webpack_require__(/*! ./_baseGetAllKeys */ "./node_modules/lodash/_baseGetAllKeys.js"),
        getSymbols = __webpack_require__(/*! ./_getSymbols */ "./node_modules/lodash/_getSymbols.js"),
        keys = __webpack_require__(/*! ./keys */ "./node_modules/lodash/keys.js");
    
    /**
     * Creates an array of own enumerable property names and symbols of `object`.
     *
     * @private
     * @param {Object} object The object to query.
     * @returns {Array} Returns the array of property names and symbols.
     */
    function getAllKeys(object) {
      return baseGetAllKeys(object, keys, getSymbols);
    }
    
    module.exports = getAllKeys;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getMapData.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_getMapData.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isKeyable = __webpack_require__(/*! ./_isKeyable */ "./node_modules/lodash/_isKeyable.js");
    
    /**
     * Gets the data for `map`.
     *
     * @private
     * @param {Object} map The map to query.
     * @param {string} key The reference key.
     * @returns {*} Returns the map data.
     */
    function getMapData(map, key) {
      var data = map.__data__;
      return isKeyable(key)
        ? data[typeof key == 'string' ? 'string' : 'hash']
        : data.map;
    }
    
    module.exports = getMapData;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getMatchData.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_getMatchData.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isStrictComparable = __webpack_require__(/*! ./_isStrictComparable */ "./node_modules/lodash/_isStrictComparable.js"),
        keys = __webpack_require__(/*! ./keys */ "./node_modules/lodash/keys.js");
    
    /**
     * Gets the property names, values, and compare flags of `object`.
     *
     * @private
     * @param {Object} object The object to query.
     * @returns {Array} Returns the match data of `object`.
     */
    function getMatchData(object) {
      var result = keys(object),
          length = result.length;
    
      while (length--) {
        var key = result[length],
            value = object[key];
    
        result[length] = [key, value, isStrictComparable(value)];
      }
      return result;
    }
    
    module.exports = getMatchData;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getNative.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_getNative.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseIsNative = __webpack_require__(/*! ./_baseIsNative */ "./node_modules/lodash/_baseIsNative.js"),
        getValue = __webpack_require__(/*! ./_getValue */ "./node_modules/lodash/_getValue.js");
    
    /**
     * Gets the native function at `key` of `object`.
     *
     * @private
     * @param {Object} object The object to query.
     * @param {string} key The key of the method to get.
     * @returns {*} Returns the function if it's native, else `undefined`.
     */
    function getNative(object, key) {
      var value = getValue(object, key);
      return baseIsNative(value) ? value : undefined;
    }
    
    module.exports = getNative;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getPrototype.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_getPrototype.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var overArg = __webpack_require__(/*! ./_overArg */ "./node_modules/lodash/_overArg.js");
    
    /** Built-in value references. */
    var getPrototype = overArg(Object.getPrototypeOf, Object);
    
    module.exports = getPrototype;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getRawTag.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_getRawTag.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var Symbol = __webpack_require__(/*! ./_Symbol */ "./node_modules/lodash/_Symbol.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * Used to resolve the
     * [`toStringTag`](http://ecma-international.org/ecma-262/7.0/#sec-object.prototype.tostring)
     * of values.
     */
    var nativeObjectToString = objectProto.toString;
    
    /** Built-in value references. */
    var symToStringTag = Symbol ? Symbol.toStringTag : undefined;
    
    /**
     * A specialized version of `baseGetTag` which ignores `Symbol.toStringTag` values.
     *
     * @private
     * @param {*} value The value to query.
     * @returns {string} Returns the raw `toStringTag`.
     */
    function getRawTag(value) {
      var isOwn = hasOwnProperty.call(value, symToStringTag),
          tag = value[symToStringTag];
    
      try {
        value[symToStringTag] = undefined;
        var unmasked = true;
      } catch (e) {}
    
      var result = nativeObjectToString.call(value);
      if (unmasked) {
        if (isOwn) {
          value[symToStringTag] = tag;
        } else {
          delete value[symToStringTag];
        }
      }
      return result;
    }
    
    module.exports = getRawTag;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getSymbols.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_getSymbols.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var arrayFilter = __webpack_require__(/*! ./_arrayFilter */ "./node_modules/lodash/_arrayFilter.js"),
        stubArray = __webpack_require__(/*! ./stubArray */ "./node_modules/lodash/stubArray.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Built-in value references. */
    var propertyIsEnumerable = objectProto.propertyIsEnumerable;
    
    /* Built-in method references for those with the same name as other `lodash` methods. */
    var nativeGetSymbols = Object.getOwnPropertySymbols;
    
    /**
     * Creates an array of the own enumerable symbols of `object`.
     *
     * @private
     * @param {Object} object The object to query.
     * @returns {Array} Returns the array of symbols.
     */
    var getSymbols = !nativeGetSymbols ? stubArray : function(object) {
      if (object == null) {
        return [];
      }
      object = Object(object);
      return arrayFilter(nativeGetSymbols(object), function(symbol) {
        return propertyIsEnumerable.call(object, symbol);
      });
    };
    
    module.exports = getSymbols;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getTag.js":
    /*!****************************************!*\
      !*** ./node_modules/lodash/_getTag.js ***!
      \****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var DataView = __webpack_require__(/*! ./_DataView */ "./node_modules/lodash/_DataView.js"),
        Map = __webpack_require__(/*! ./_Map */ "./node_modules/lodash/_Map.js"),
        Promise = __webpack_require__(/*! ./_Promise */ "./node_modules/lodash/_Promise.js"),
        Set = __webpack_require__(/*! ./_Set */ "./node_modules/lodash/_Set.js"),
        WeakMap = __webpack_require__(/*! ./_WeakMap */ "./node_modules/lodash/_WeakMap.js"),
        baseGetTag = __webpack_require__(/*! ./_baseGetTag */ "./node_modules/lodash/_baseGetTag.js"),
        toSource = __webpack_require__(/*! ./_toSource */ "./node_modules/lodash/_toSource.js");
    
    /** `Object#toString` result references. */
    var mapTag = '[object Map]',
        objectTag = '[object Object]',
        promiseTag = '[object Promise]',
        setTag = '[object Set]',
        weakMapTag = '[object WeakMap]';
    
    var dataViewTag = '[object DataView]';
    
    /** Used to detect maps, sets, and weakmaps. */
    var dataViewCtorString = toSource(DataView),
        mapCtorString = toSource(Map),
        promiseCtorString = toSource(Promise),
        setCtorString = toSource(Set),
        weakMapCtorString = toSource(WeakMap);
    
    /**
     * Gets the `toStringTag` of `value`.
     *
     * @private
     * @param {*} value The value to query.
     * @returns {string} Returns the `toStringTag`.
     */
    var getTag = baseGetTag;
    
    // Fallback for data views, maps, sets, and weak maps in IE 11 and promises in Node.js < 6.
    if ((DataView && getTag(new DataView(new ArrayBuffer(1))) != dataViewTag) ||
        (Map && getTag(new Map) != mapTag) ||
        (Promise && getTag(Promise.resolve()) != promiseTag) ||
        (Set && getTag(new Set) != setTag) ||
        (WeakMap && getTag(new WeakMap) != weakMapTag)) {
      getTag = function(value) {
        var result = baseGetTag(value),
            Ctor = result == objectTag ? value.constructor : undefined,
            ctorString = Ctor ? toSource(Ctor) : '';
    
        if (ctorString) {
          switch (ctorString) {
            case dataViewCtorString: return dataViewTag;
            case mapCtorString: return mapTag;
            case promiseCtorString: return promiseTag;
            case setCtorString: return setTag;
            case weakMapCtorString: return weakMapTag;
          }
        }
        return result;
      };
    }
    
    module.exports = getTag;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_getValue.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_getValue.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Gets the value at `key` of `object`.
     *
     * @private
     * @param {Object} [object] The object to query.
     * @param {string} key The key of the property to get.
     * @returns {*} Returns the property value.
     */
    function getValue(object, key) {
      return object == null ? undefined : object[key];
    }
    
    module.exports = getValue;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hasPath.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_hasPath.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var castPath = __webpack_require__(/*! ./_castPath */ "./node_modules/lodash/_castPath.js"),
        isArguments = __webpack_require__(/*! ./isArguments */ "./node_modules/lodash/isArguments.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        isIndex = __webpack_require__(/*! ./_isIndex */ "./node_modules/lodash/_isIndex.js"),
        isLength = __webpack_require__(/*! ./isLength */ "./node_modules/lodash/isLength.js"),
        toKey = __webpack_require__(/*! ./_toKey */ "./node_modules/lodash/_toKey.js");
    
    /**
     * Checks if `path` exists on `object`.
     *
     * @private
     * @param {Object} object The object to query.
     * @param {Array|string} path The path to check.
     * @param {Function} hasFunc The function to check properties.
     * @returns {boolean} Returns `true` if `path` exists, else `false`.
     */
    function hasPath(object, path, hasFunc) {
      path = castPath(path, object);
    
      var index = -1,
          length = path.length,
          result = false;
    
      while (++index < length) {
        var key = toKey(path[index]);
        if (!(result = object != null && hasFunc(object, key))) {
          break;
        }
        object = object[key];
      }
      if (result || ++index != length) {
        return result;
      }
      length = object == null ? 0 : object.length;
      return !!length && isLength(length) && isIndex(key, length) &&
        (isArray(object) || isArguments(object));
    }
    
    module.exports = hasPath;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hasUnicode.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_hasUnicode.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used to compose unicode character classes. */
    var rsAstralRange = '\\ud800-\\udfff',
        rsComboMarksRange = '\\u0300-\\u036f',
        reComboHalfMarksRange = '\\ufe20-\\ufe2f',
        rsComboSymbolsRange = '\\u20d0-\\u20ff',
        rsComboRange = rsComboMarksRange + reComboHalfMarksRange + rsComboSymbolsRange,
        rsVarRange = '\\ufe0e\\ufe0f';
    
    /** Used to compose unicode capture groups. */
    var rsZWJ = '\\u200d';
    
    /** Used to detect strings with [zero-width joiners or code points from the astral planes](http://eev.ee/blog/2015/09/12/dark-corners-of-unicode/). */
    var reHasUnicode = RegExp('[' + rsZWJ + rsAstralRange  + rsComboRange + rsVarRange + ']');
    
    /**
     * Checks if `string` contains Unicode symbols.
     *
     * @private
     * @param {string} string The string to inspect.
     * @returns {boolean} Returns `true` if a symbol is found, else `false`.
     */
    function hasUnicode(string) {
      return reHasUnicode.test(string);
    }
    
    module.exports = hasUnicode;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hasUnicodeWord.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_hasUnicodeWord.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used to detect strings that need a more robust regexp to match words. */
    var reHasUnicodeWord = /[a-z][A-Z]|[A-Z]{2}[a-z]|[0-9][a-zA-Z]|[a-zA-Z][0-9]|[^a-zA-Z0-9 ]/;
    
    /**
     * Checks if `string` contains a word composed of Unicode symbols.
     *
     * @private
     * @param {string} string The string to inspect.
     * @returns {boolean} Returns `true` if a word is found, else `false`.
     */
    function hasUnicodeWord(string) {
      return reHasUnicodeWord.test(string);
    }
    
    module.exports = hasUnicodeWord;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hashClear.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_hashClear.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ "./node_modules/lodash/_nativeCreate.js");
    
    /**
     * Removes all key-value entries from the hash.
     *
     * @private
     * @name clear
     * @memberOf Hash
     */
    function hashClear() {
      this.__data__ = nativeCreate ? nativeCreate(null) : {};
      this.size = 0;
    }
    
    module.exports = hashClear;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hashDelete.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_hashDelete.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Removes `key` and its value from the hash.
     *
     * @private
     * @name delete
     * @memberOf Hash
     * @param {Object} hash The hash to modify.
     * @param {string} key The key of the value to remove.
     * @returns {boolean} Returns `true` if the entry was removed, else `false`.
     */
    function hashDelete(key) {
      var result = this.has(key) && delete this.__data__[key];
      this.size -= result ? 1 : 0;
      return result;
    }
    
    module.exports = hashDelete;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hashGet.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_hashGet.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ "./node_modules/lodash/_nativeCreate.js");
    
    /** Used to stand-in for `undefined` hash values. */
    var HASH_UNDEFINED = '__lodash_hash_undefined__';
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * Gets the hash value for `key`.
     *
     * @private
     * @name get
     * @memberOf Hash
     * @param {string} key The key of the value to get.
     * @returns {*} Returns the entry value.
     */
    function hashGet(key) {
      var data = this.__data__;
      if (nativeCreate) {
        var result = data[key];
        return result === HASH_UNDEFINED ? undefined : result;
      }
      return hasOwnProperty.call(data, key) ? data[key] : undefined;
    }
    
    module.exports = hashGet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hashHas.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_hashHas.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ "./node_modules/lodash/_nativeCreate.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * Checks if a hash value for `key` exists.
     *
     * @private
     * @name has
     * @memberOf Hash
     * @param {string} key The key of the entry to check.
     * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
     */
    function hashHas(key) {
      var data = this.__data__;
      return nativeCreate ? (data[key] !== undefined) : hasOwnProperty.call(data, key);
    }
    
    module.exports = hashHas;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_hashSet.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_hashSet.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var nativeCreate = __webpack_require__(/*! ./_nativeCreate */ "./node_modules/lodash/_nativeCreate.js");
    
    /** Used to stand-in for `undefined` hash values. */
    var HASH_UNDEFINED = '__lodash_hash_undefined__';
    
    /**
     * Sets the hash `key` to `value`.
     *
     * @private
     * @name set
     * @memberOf Hash
     * @param {string} key The key of the value to set.
     * @param {*} value The value to set.
     * @returns {Object} Returns the hash instance.
     */
    function hashSet(key, value) {
      var data = this.__data__;
      this.size += this.has(key) ? 0 : 1;
      data[key] = (nativeCreate && value === undefined) ? HASH_UNDEFINED : value;
      return this;
    }
    
    module.exports = hashSet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_isIndex.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_isIndex.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used as references for various `Number` constants. */
    var MAX_SAFE_INTEGER = 9007199254740991;
    
    /** Used to detect unsigned integer values. */
    var reIsUint = /^(?:0|[1-9]\d*)$/;
    
    /**
     * Checks if `value` is a valid array-like index.
     *
     * @private
     * @param {*} value The value to check.
     * @param {number} [length=MAX_SAFE_INTEGER] The upper bounds of a valid index.
     * @returns {boolean} Returns `true` if `value` is a valid index, else `false`.
     */
    function isIndex(value, length) {
      var type = typeof value;
      length = length == null ? MAX_SAFE_INTEGER : length;
    
      return !!length &&
        (type == 'number' ||
          (type != 'symbol' && reIsUint.test(value))) &&
            (value > -1 && value % 1 == 0 && value < length);
    }
    
    module.exports = isIndex;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_isIterateeCall.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_isIterateeCall.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var eq = __webpack_require__(/*! ./eq */ "./node_modules/lodash/eq.js"),
        isArrayLike = __webpack_require__(/*! ./isArrayLike */ "./node_modules/lodash/isArrayLike.js"),
        isIndex = __webpack_require__(/*! ./_isIndex */ "./node_modules/lodash/_isIndex.js"),
        isObject = __webpack_require__(/*! ./isObject */ "./node_modules/lodash/isObject.js");
    
    /**
     * Checks if the given arguments are from an iteratee call.
     *
     * @private
     * @param {*} value The potential iteratee value argument.
     * @param {*} index The potential iteratee index or key argument.
     * @param {*} object The potential iteratee object argument.
     * @returns {boolean} Returns `true` if the arguments are from an iteratee call,
     *  else `false`.
     */
    function isIterateeCall(value, index, object) {
      if (!isObject(object)) {
        return false;
      }
      var type = typeof index;
      if (type == 'number'
            ? (isArrayLike(object) && isIndex(index, object.length))
            : (type == 'string' && index in object)
          ) {
        return eq(object[index], value);
      }
      return false;
    }
    
    module.exports = isIterateeCall;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_isKey.js":
    /*!***************************************!*\
      !*** ./node_modules/lodash/_isKey.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        isSymbol = __webpack_require__(/*! ./isSymbol */ "./node_modules/lodash/isSymbol.js");
    
    /** Used to match property names within property paths. */
    var reIsDeepProp = /\.|\[(?:[^[\]]*|(["'])(?:(?!\1)[^\\]|\\.)*?\1)\]/,
        reIsPlainProp = /^\w*$/;
    
    /**
     * Checks if `value` is a property name and not a property path.
     *
     * @private
     * @param {*} value The value to check.
     * @param {Object} [object] The object to query keys on.
     * @returns {boolean} Returns `true` if `value` is a property name, else `false`.
     */
    function isKey(value, object) {
      if (isArray(value)) {
        return false;
      }
      var type = typeof value;
      if (type == 'number' || type == 'symbol' || type == 'boolean' ||
          value == null || isSymbol(value)) {
        return true;
      }
      return reIsPlainProp.test(value) || !reIsDeepProp.test(value) ||
        (object != null && value in Object(object));
    }
    
    module.exports = isKey;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_isKeyable.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/_isKeyable.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if `value` is suitable for use as unique object key.
     *
     * @private
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is suitable, else `false`.
     */
    function isKeyable(value) {
      var type = typeof value;
      return (type == 'string' || type == 'number' || type == 'symbol' || type == 'boolean')
        ? (value !== '__proto__')
        : (value === null);
    }
    
    module.exports = isKeyable;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_isMasked.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_isMasked.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var coreJsData = __webpack_require__(/*! ./_coreJsData */ "./node_modules/lodash/_coreJsData.js");
    
    /** Used to detect methods masquerading as native. */
    var maskSrcKey = (function() {
      var uid = /[^.]+$/.exec(coreJsData && coreJsData.keys && coreJsData.keys.IE_PROTO || '');
      return uid ? ('Symbol(src)_1.' + uid) : '';
    }());
    
    /**
     * Checks if `func` has its source masked.
     *
     * @private
     * @param {Function} func The function to check.
     * @returns {boolean} Returns `true` if `func` is masked, else `false`.
     */
    function isMasked(func) {
      return !!maskSrcKey && (maskSrcKey in func);
    }
    
    module.exports = isMasked;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_isPrototype.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_isPrototype.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /**
     * Checks if `value` is likely a prototype object.
     *
     * @private
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a prototype, else `false`.
     */
    function isPrototype(value) {
      var Ctor = value && value.constructor,
          proto = (typeof Ctor == 'function' && Ctor.prototype) || objectProto;
    
      return value === proto;
    }
    
    module.exports = isPrototype;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_isStrictComparable.js":
    /*!****************************************************!*\
      !*** ./node_modules/lodash/_isStrictComparable.js ***!
      \****************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isObject = __webpack_require__(/*! ./isObject */ "./node_modules/lodash/isObject.js");
    
    /**
     * Checks if `value` is suitable for strict equality comparisons, i.e. `===`.
     *
     * @private
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` if suitable for strict
     *  equality comparisons, else `false`.
     */
    function isStrictComparable(value) {
      return value === value && !isObject(value);
    }
    
    module.exports = isStrictComparable;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_listCacheClear.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_listCacheClear.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Removes all key-value entries from the list cache.
     *
     * @private
     * @name clear
     * @memberOf ListCache
     */
    function listCacheClear() {
      this.__data__ = [];
      this.size = 0;
    }
    
    module.exports = listCacheClear;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_listCacheDelete.js":
    /*!*************************************************!*\
      !*** ./node_modules/lodash/_listCacheDelete.js ***!
      \*************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ "./node_modules/lodash/_assocIndexOf.js");
    
    /** Used for built-in method references. */
    var arrayProto = Array.prototype;
    
    /** Built-in value references. */
    var splice = arrayProto.splice;
    
    /**
     * Removes `key` and its value from the list cache.
     *
     * @private
     * @name delete
     * @memberOf ListCache
     * @param {string} key The key of the value to remove.
     * @returns {boolean} Returns `true` if the entry was removed, else `false`.
     */
    function listCacheDelete(key) {
      var data = this.__data__,
          index = assocIndexOf(data, key);
    
      if (index < 0) {
        return false;
      }
      var lastIndex = data.length - 1;
      if (index == lastIndex) {
        data.pop();
      } else {
        splice.call(data, index, 1);
      }
      --this.size;
      return true;
    }
    
    module.exports = listCacheDelete;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_listCacheGet.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_listCacheGet.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ "./node_modules/lodash/_assocIndexOf.js");
    
    /**
     * Gets the list cache value for `key`.
     *
     * @private
     * @name get
     * @memberOf ListCache
     * @param {string} key The key of the value to get.
     * @returns {*} Returns the entry value.
     */
    function listCacheGet(key) {
      var data = this.__data__,
          index = assocIndexOf(data, key);
    
      return index < 0 ? undefined : data[index][1];
    }
    
    module.exports = listCacheGet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_listCacheHas.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_listCacheHas.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ "./node_modules/lodash/_assocIndexOf.js");
    
    /**
     * Checks if a list cache value for `key` exists.
     *
     * @private
     * @name has
     * @memberOf ListCache
     * @param {string} key The key of the entry to check.
     * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
     */
    function listCacheHas(key) {
      return assocIndexOf(this.__data__, key) > -1;
    }
    
    module.exports = listCacheHas;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_listCacheSet.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_listCacheSet.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var assocIndexOf = __webpack_require__(/*! ./_assocIndexOf */ "./node_modules/lodash/_assocIndexOf.js");
    
    /**
     * Sets the list cache `key` to `value`.
     *
     * @private
     * @name set
     * @memberOf ListCache
     * @param {string} key The key of the value to set.
     * @param {*} value The value to set.
     * @returns {Object} Returns the list cache instance.
     */
    function listCacheSet(key, value) {
      var data = this.__data__,
          index = assocIndexOf(data, key);
    
      if (index < 0) {
        ++this.size;
        data.push([key, value]);
      } else {
        data[index][1] = value;
      }
      return this;
    }
    
    module.exports = listCacheSet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_mapCacheClear.js":
    /*!***********************************************!*\
      !*** ./node_modules/lodash/_mapCacheClear.js ***!
      \***********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var Hash = __webpack_require__(/*! ./_Hash */ "./node_modules/lodash/_Hash.js"),
        ListCache = __webpack_require__(/*! ./_ListCache */ "./node_modules/lodash/_ListCache.js"),
        Map = __webpack_require__(/*! ./_Map */ "./node_modules/lodash/_Map.js");
    
    /**
     * Removes all key-value entries from the map.
     *
     * @private
     * @name clear
     * @memberOf MapCache
     */
    function mapCacheClear() {
      this.size = 0;
      this.__data__ = {
        'hash': new Hash,
        'map': new (Map || ListCache),
        'string': new Hash
      };
    }
    
    module.exports = mapCacheClear;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_mapCacheDelete.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_mapCacheDelete.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getMapData = __webpack_require__(/*! ./_getMapData */ "./node_modules/lodash/_getMapData.js");
    
    /**
     * Removes `key` and its value from the map.
     *
     * @private
     * @name delete
     * @memberOf MapCache
     * @param {string} key The key of the value to remove.
     * @returns {boolean} Returns `true` if the entry was removed, else `false`.
     */
    function mapCacheDelete(key) {
      var result = getMapData(this, key)['delete'](key);
      this.size -= result ? 1 : 0;
      return result;
    }
    
    module.exports = mapCacheDelete;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_mapCacheGet.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_mapCacheGet.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getMapData = __webpack_require__(/*! ./_getMapData */ "./node_modules/lodash/_getMapData.js");
    
    /**
     * Gets the map value for `key`.
     *
     * @private
     * @name get
     * @memberOf MapCache
     * @param {string} key The key of the value to get.
     * @returns {*} Returns the entry value.
     */
    function mapCacheGet(key) {
      return getMapData(this, key).get(key);
    }
    
    module.exports = mapCacheGet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_mapCacheHas.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_mapCacheHas.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getMapData = __webpack_require__(/*! ./_getMapData */ "./node_modules/lodash/_getMapData.js");
    
    /**
     * Checks if a map value for `key` exists.
     *
     * @private
     * @name has
     * @memberOf MapCache
     * @param {string} key The key of the entry to check.
     * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
     */
    function mapCacheHas(key) {
      return getMapData(this, key).has(key);
    }
    
    module.exports = mapCacheHas;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_mapCacheSet.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_mapCacheSet.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getMapData = __webpack_require__(/*! ./_getMapData */ "./node_modules/lodash/_getMapData.js");
    
    /**
     * Sets the map `key` to `value`.
     *
     * @private
     * @name set
     * @memberOf MapCache
     * @param {string} key The key of the value to set.
     * @param {*} value The value to set.
     * @returns {Object} Returns the map cache instance.
     */
    function mapCacheSet(key, value) {
      var data = getMapData(this, key),
          size = data.size;
    
      data.set(key, value);
      this.size += data.size == size ? 0 : 1;
      return this;
    }
    
    module.exports = mapCacheSet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_mapToArray.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_mapToArray.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Converts `map` to its key-value pairs.
     *
     * @private
     * @param {Object} map The map to convert.
     * @returns {Array} Returns the key-value pairs.
     */
    function mapToArray(map) {
      var index = -1,
          result = Array(map.size);
    
      map.forEach(function(value, key) {
        result[++index] = [key, value];
      });
      return result;
    }
    
    module.exports = mapToArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_matchesStrictComparable.js":
    /*!*********************************************************!*\
      !*** ./node_modules/lodash/_matchesStrictComparable.js ***!
      \*********************************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * A specialized version of `matchesProperty` for source values suitable
     * for strict equality comparisons, i.e. `===`.
     *
     * @private
     * @param {string} key The key of the property to get.
     * @param {*} srcValue The value to match.
     * @returns {Function} Returns the new spec function.
     */
    function matchesStrictComparable(key, srcValue) {
      return function(object) {
        if (object == null) {
          return false;
        }
        return object[key] === srcValue &&
          (srcValue !== undefined || (key in Object(object)));
      };
    }
    
    module.exports = matchesStrictComparable;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_memoizeCapped.js":
    /*!***********************************************!*\
      !*** ./node_modules/lodash/_memoizeCapped.js ***!
      \***********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var memoize = __webpack_require__(/*! ./memoize */ "./node_modules/lodash/memoize.js");
    
    /** Used as the maximum memoize cache size. */
    var MAX_MEMOIZE_SIZE = 500;
    
    /**
     * A specialized version of `_.memoize` which clears the memoized function's
     * cache when it exceeds `MAX_MEMOIZE_SIZE`.
     *
     * @private
     * @param {Function} func The function to have its output memoized.
     * @returns {Function} Returns the new memoized function.
     */
    function memoizeCapped(func) {
      var result = memoize(func, function(key) {
        if (cache.size === MAX_MEMOIZE_SIZE) {
          cache.clear();
        }
        return key;
      });
    
      var cache = result.cache;
      return result;
    }
    
    module.exports = memoizeCapped;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_nativeCreate.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_nativeCreate.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var getNative = __webpack_require__(/*! ./_getNative */ "./node_modules/lodash/_getNative.js");
    
    /* Built-in method references that are verified to be native. */
    var nativeCreate = getNative(Object, 'create');
    
    module.exports = nativeCreate;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_nativeKeys.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_nativeKeys.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var overArg = __webpack_require__(/*! ./_overArg */ "./node_modules/lodash/_overArg.js");
    
    /* Built-in method references for those with the same name as other `lodash` methods. */
    var nativeKeys = overArg(Object.keys, Object);
    
    module.exports = nativeKeys;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_nativeKeysIn.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_nativeKeysIn.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * This function is like
     * [`Object.keys`](http://ecma-international.org/ecma-262/7.0/#sec-object.keys)
     * except that it includes inherited enumerable properties.
     *
     * @private
     * @param {Object} object The object to query.
     * @returns {Array} Returns the array of property names.
     */
    function nativeKeysIn(object) {
      var result = [];
      if (object != null) {
        for (var key in Object(object)) {
          result.push(key);
        }
      }
      return result;
    }
    
    module.exports = nativeKeysIn;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_nodeUtil.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_nodeUtil.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    /* WEBPACK VAR INJECTION */(function(module) {var freeGlobal = __webpack_require__(/*! ./_freeGlobal */ "./node_modules/lodash/_freeGlobal.js");
    
    /** Detect free variable `exports`. */
    var freeExports =  true && exports && !exports.nodeType && exports;
    
    /** Detect free variable `module`. */
    var freeModule = freeExports && typeof module == 'object' && module && !module.nodeType && module;
    
    /** Detect the popular CommonJS extension `module.exports`. */
    var moduleExports = freeModule && freeModule.exports === freeExports;
    
    /** Detect free variable `process` from Node.js. */
    var freeProcess = moduleExports && freeGlobal.process;
    
    /** Used to access faster Node.js helpers. */
    var nodeUtil = (function() {
      try {
        // Use `util.types` for Node.js 10+.
        var types = freeModule && freeModule.require && freeModule.require('util').types;
    
        if (types) {
          return types;
        }
    
        // Legacy `process.binding('util')` for Node.js < 10.
        return freeProcess && freeProcess.binding && freeProcess.binding('util');
      } catch (e) {}
    }());
    
    module.exports = nodeUtil;
    
    /* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./../webpack/buildin/module.js */ "./node_modules/webpack/buildin/module.js")(module)))
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_objectToString.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_objectToString.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /**
     * Used to resolve the
     * [`toStringTag`](http://ecma-international.org/ecma-262/7.0/#sec-object.prototype.tostring)
     * of values.
     */
    var nativeObjectToString = objectProto.toString;
    
    /**
     * Converts `value` to a string using `Object.prototype.toString`.
     *
     * @private
     * @param {*} value The value to convert.
     * @returns {string} Returns the converted string.
     */
    function objectToString(value) {
      return nativeObjectToString.call(value);
    }
    
    module.exports = objectToString;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_overArg.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/_overArg.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Creates a unary function that invokes `func` with its argument transformed.
     *
     * @private
     * @param {Function} func The function to wrap.
     * @param {Function} transform The argument transform.
     * @returns {Function} Returns the new function.
     */
    function overArg(func, transform) {
      return function(arg) {
        return func(transform(arg));
      };
    }
    
    module.exports = overArg;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_overRest.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_overRest.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var apply = __webpack_require__(/*! ./_apply */ "./node_modules/lodash/_apply.js");
    
    /* Built-in method references for those with the same name as other `lodash` methods. */
    var nativeMax = Math.max;
    
    /**
     * A specialized version of `baseRest` which transforms the rest array.
     *
     * @private
     * @param {Function} func The function to apply a rest parameter to.
     * @param {number} [start=func.length-1] The start position of the rest parameter.
     * @param {Function} transform The rest array transform.
     * @returns {Function} Returns the new function.
     */
    function overRest(func, start, transform) {
      start = nativeMax(start === undefined ? (func.length - 1) : start, 0);
      return function() {
        var args = arguments,
            index = -1,
            length = nativeMax(args.length - start, 0),
            array = Array(length);
    
        while (++index < length) {
          array[index] = args[start + index];
        }
        index = -1;
        var otherArgs = Array(start + 1);
        while (++index < start) {
          otherArgs[index] = args[index];
        }
        otherArgs[start] = transform(array);
        return apply(func, this, otherArgs);
      };
    }
    
    module.exports = overRest;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_root.js":
    /*!**************************************!*\
      !*** ./node_modules/lodash/_root.js ***!
      \**************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var freeGlobal = __webpack_require__(/*! ./_freeGlobal */ "./node_modules/lodash/_freeGlobal.js");
    
    /** Detect free variable `self`. */
    var freeSelf = typeof self == 'object' && self && self.Object === Object && self;
    
    /** Used as a reference to the global object. */
    var root = freeGlobal || freeSelf || Function('return this')();
    
    module.exports = root;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_setCacheAdd.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_setCacheAdd.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used to stand-in for `undefined` hash values. */
    var HASH_UNDEFINED = '__lodash_hash_undefined__';
    
    /**
     * Adds `value` to the array cache.
     *
     * @private
     * @name add
     * @memberOf SetCache
     * @alias push
     * @param {*} value The value to cache.
     * @returns {Object} Returns the cache instance.
     */
    function setCacheAdd(value) {
      this.__data__.set(value, HASH_UNDEFINED);
      return this;
    }
    
    module.exports = setCacheAdd;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_setCacheHas.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_setCacheHas.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if `value` is in the array cache.
     *
     * @private
     * @name has
     * @memberOf SetCache
     * @param {*} value The value to search for.
     * @returns {number} Returns `true` if `value` is found, else `false`.
     */
    function setCacheHas(value) {
      return this.__data__.has(value);
    }
    
    module.exports = setCacheHas;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_setToArray.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_setToArray.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Converts `set` to an array of its values.
     *
     * @private
     * @param {Object} set The set to convert.
     * @returns {Array} Returns the values.
     */
    function setToArray(set) {
      var index = -1,
          result = Array(set.size);
    
      set.forEach(function(value) {
        result[++index] = value;
      });
      return result;
    }
    
    module.exports = setToArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_setToString.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_setToString.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseSetToString = __webpack_require__(/*! ./_baseSetToString */ "./node_modules/lodash/_baseSetToString.js"),
        shortOut = __webpack_require__(/*! ./_shortOut */ "./node_modules/lodash/_shortOut.js");
    
    /**
     * Sets the `toString` method of `func` to return `string`.
     *
     * @private
     * @param {Function} func The function to modify.
     * @param {Function} string The `toString` result.
     * @returns {Function} Returns `func`.
     */
    var setToString = shortOut(baseSetToString);
    
    module.exports = setToString;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_shortOut.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_shortOut.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used to detect hot functions by number of calls within a span of milliseconds. */
    var HOT_COUNT = 800,
        HOT_SPAN = 16;
    
    /* Built-in method references for those with the same name as other `lodash` methods. */
    var nativeNow = Date.now;
    
    /**
     * Creates a function that'll short out and invoke `identity` instead
     * of `func` when it's called `HOT_COUNT` or more times in `HOT_SPAN`
     * milliseconds.
     *
     * @private
     * @param {Function} func The function to restrict.
     * @returns {Function} Returns the new shortable function.
     */
    function shortOut(func) {
      var count = 0,
          lastCalled = 0;
    
      return function() {
        var stamp = nativeNow(),
            remaining = HOT_SPAN - (stamp - lastCalled);
    
        lastCalled = stamp;
        if (remaining > 0) {
          if (++count >= HOT_COUNT) {
            return arguments[0];
          }
        } else {
          count = 0;
        }
        return func.apply(undefined, arguments);
      };
    }
    
    module.exports = shortOut;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_stackClear.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/_stackClear.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var ListCache = __webpack_require__(/*! ./_ListCache */ "./node_modules/lodash/_ListCache.js");
    
    /**
     * Removes all key-value entries from the stack.
     *
     * @private
     * @name clear
     * @memberOf Stack
     */
    function stackClear() {
      this.__data__ = new ListCache;
      this.size = 0;
    }
    
    module.exports = stackClear;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_stackDelete.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/_stackDelete.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Removes `key` and its value from the stack.
     *
     * @private
     * @name delete
     * @memberOf Stack
     * @param {string} key The key of the value to remove.
     * @returns {boolean} Returns `true` if the entry was removed, else `false`.
     */
    function stackDelete(key) {
      var data = this.__data__,
          result = data['delete'](key);
    
      this.size = data.size;
      return result;
    }
    
    module.exports = stackDelete;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_stackGet.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_stackGet.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Gets the stack value for `key`.
     *
     * @private
     * @name get
     * @memberOf Stack
     * @param {string} key The key of the value to get.
     * @returns {*} Returns the entry value.
     */
    function stackGet(key) {
      return this.__data__.get(key);
    }
    
    module.exports = stackGet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_stackHas.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_stackHas.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if a stack value for `key` exists.
     *
     * @private
     * @name has
     * @memberOf Stack
     * @param {string} key The key of the entry to check.
     * @returns {boolean} Returns `true` if an entry for `key` exists, else `false`.
     */
    function stackHas(key) {
      return this.__data__.has(key);
    }
    
    module.exports = stackHas;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_stackSet.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_stackSet.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var ListCache = __webpack_require__(/*! ./_ListCache */ "./node_modules/lodash/_ListCache.js"),
        Map = __webpack_require__(/*! ./_Map */ "./node_modules/lodash/_Map.js"),
        MapCache = __webpack_require__(/*! ./_MapCache */ "./node_modules/lodash/_MapCache.js");
    
    /** Used as the size to enable large array optimizations. */
    var LARGE_ARRAY_SIZE = 200;
    
    /**
     * Sets the stack `key` to `value`.
     *
     * @private
     * @name set
     * @memberOf Stack
     * @param {string} key The key of the value to set.
     * @param {*} value The value to set.
     * @returns {Object} Returns the stack cache instance.
     */
    function stackSet(key, value) {
      var data = this.__data__;
      if (data instanceof ListCache) {
        var pairs = data.__data__;
        if (!Map || (pairs.length < LARGE_ARRAY_SIZE - 1)) {
          pairs.push([key, value]);
          this.size = ++data.size;
          return this;
        }
        data = this.__data__ = new MapCache(pairs);
      }
      data.set(key, value);
      this.size = data.size;
      return this;
    }
    
    module.exports = stackSet;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_stringToArray.js":
    /*!***********************************************!*\
      !*** ./node_modules/lodash/_stringToArray.js ***!
      \***********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var asciiToArray = __webpack_require__(/*! ./_asciiToArray */ "./node_modules/lodash/_asciiToArray.js"),
        hasUnicode = __webpack_require__(/*! ./_hasUnicode */ "./node_modules/lodash/_hasUnicode.js"),
        unicodeToArray = __webpack_require__(/*! ./_unicodeToArray */ "./node_modules/lodash/_unicodeToArray.js");
    
    /**
     * Converts `string` to an array.
     *
     * @private
     * @param {string} string The string to convert.
     * @returns {Array} Returns the converted array.
     */
    function stringToArray(string) {
      return hasUnicode(string)
        ? unicodeToArray(string)
        : asciiToArray(string);
    }
    
    module.exports = stringToArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_stringToPath.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_stringToPath.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var memoizeCapped = __webpack_require__(/*! ./_memoizeCapped */ "./node_modules/lodash/_memoizeCapped.js");
    
    /** Used to match property names within property paths. */
    var rePropName = /[^.[\]]+|\[(?:(-?\d+(?:\.\d+)?)|(["'])((?:(?!\2)[^\\]|\\.)*?)\2)\]|(?=(?:\.|\[\])(?:\.|\[\]|$))/g;
    
    /** Used to match backslashes in property paths. */
    var reEscapeChar = /\\(\\)?/g;
    
    /**
     * Converts `string` to a property path array.
     *
     * @private
     * @param {string} string The string to convert.
     * @returns {Array} Returns the property path array.
     */
    var stringToPath = memoizeCapped(function(string) {
      var result = [];
      if (string.charCodeAt(0) === 46 /* . */) {
        result.push('');
      }
      string.replace(rePropName, function(match, number, quote, subString) {
        result.push(quote ? subString.replace(reEscapeChar, '$1') : (number || match));
      });
      return result;
    });
    
    module.exports = stringToPath;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_toKey.js":
    /*!***************************************!*\
      !*** ./node_modules/lodash/_toKey.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isSymbol = __webpack_require__(/*! ./isSymbol */ "./node_modules/lodash/isSymbol.js");
    
    /** Used as references for various `Number` constants. */
    var INFINITY = 1 / 0;
    
    /**
     * Converts `value` to a string key if it's not a string or symbol.
     *
     * @private
     * @param {*} value The value to inspect.
     * @returns {string|symbol} Returns the key.
     */
    function toKey(value) {
      if (typeof value == 'string' || isSymbol(value)) {
        return value;
      }
      var result = (value + '');
      return (result == '0' && (1 / value) == -INFINITY) ? '-0' : result;
    }
    
    module.exports = toKey;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_toSource.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/_toSource.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used for built-in method references. */
    var funcProto = Function.prototype;
    
    /** Used to resolve the decompiled source of functions. */
    var funcToString = funcProto.toString;
    
    /**
     * Converts `func` to its source code.
     *
     * @private
     * @param {Function} func The function to convert.
     * @returns {string} Returns the source code.
     */
    function toSource(func) {
      if (func != null) {
        try {
          return funcToString.call(func);
        } catch (e) {}
        try {
          return (func + '');
        } catch (e) {}
      }
      return '';
    }
    
    module.exports = toSource;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_unicodeToArray.js":
    /*!************************************************!*\
      !*** ./node_modules/lodash/_unicodeToArray.js ***!
      \************************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used to compose unicode character classes. */
    var rsAstralRange = '\\ud800-\\udfff',
        rsComboMarksRange = '\\u0300-\\u036f',
        reComboHalfMarksRange = '\\ufe20-\\ufe2f',
        rsComboSymbolsRange = '\\u20d0-\\u20ff',
        rsComboRange = rsComboMarksRange + reComboHalfMarksRange + rsComboSymbolsRange,
        rsVarRange = '\\ufe0e\\ufe0f';
    
    /** Used to compose unicode capture groups. */
    var rsAstral = '[' + rsAstralRange + ']',
        rsCombo = '[' + rsComboRange + ']',
        rsFitz = '\\ud83c[\\udffb-\\udfff]',
        rsModifier = '(?:' + rsCombo + '|' + rsFitz + ')',
        rsNonAstral = '[^' + rsAstralRange + ']',
        rsRegional = '(?:\\ud83c[\\udde6-\\uddff]){2}',
        rsSurrPair = '[\\ud800-\\udbff][\\udc00-\\udfff]',
        rsZWJ = '\\u200d';
    
    /** Used to compose unicode regexes. */
    var reOptMod = rsModifier + '?',
        rsOptVar = '[' + rsVarRange + ']?',
        rsOptJoin = '(?:' + rsZWJ + '(?:' + [rsNonAstral, rsRegional, rsSurrPair].join('|') + ')' + rsOptVar + reOptMod + ')*',
        rsSeq = rsOptVar + reOptMod + rsOptJoin,
        rsSymbol = '(?:' + [rsNonAstral + rsCombo + '?', rsCombo, rsRegional, rsSurrPair, rsAstral].join('|') + ')';
    
    /** Used to match [string symbols](https://mathiasbynens.be/notes/javascript-unicode). */
    var reUnicode = RegExp(rsFitz + '(?=' + rsFitz + ')|' + rsSymbol + rsSeq, 'g');
    
    /**
     * Converts a Unicode `string` to an array.
     *
     * @private
     * @param {string} string The string to convert.
     * @returns {Array} Returns the converted array.
     */
    function unicodeToArray(string) {
      return string.match(reUnicode) || [];
    }
    
    module.exports = unicodeToArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/_unicodeWords.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/_unicodeWords.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used to compose unicode character classes. */
    var rsAstralRange = '\\ud800-\\udfff',
        rsComboMarksRange = '\\u0300-\\u036f',
        reComboHalfMarksRange = '\\ufe20-\\ufe2f',
        rsComboSymbolsRange = '\\u20d0-\\u20ff',
        rsComboRange = rsComboMarksRange + reComboHalfMarksRange + rsComboSymbolsRange,
        rsDingbatRange = '\\u2700-\\u27bf',
        rsLowerRange = 'a-z\\xdf-\\xf6\\xf8-\\xff',
        rsMathOpRange = '\\xac\\xb1\\xd7\\xf7',
        rsNonCharRange = '\\x00-\\x2f\\x3a-\\x40\\x5b-\\x60\\x7b-\\xbf',
        rsPunctuationRange = '\\u2000-\\u206f',
        rsSpaceRange = ' \\t\\x0b\\f\\xa0\\ufeff\\n\\r\\u2028\\u2029\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000',
        rsUpperRange = 'A-Z\\xc0-\\xd6\\xd8-\\xde',
        rsVarRange = '\\ufe0e\\ufe0f',
        rsBreakRange = rsMathOpRange + rsNonCharRange + rsPunctuationRange + rsSpaceRange;
    
    /** Used to compose unicode capture groups. */
    var rsApos = "['\u2019]",
        rsBreak = '[' + rsBreakRange + ']',
        rsCombo = '[' + rsComboRange + ']',
        rsDigits = '\\d+',
        rsDingbat = '[' + rsDingbatRange + ']',
        rsLower = '[' + rsLowerRange + ']',
        rsMisc = '[^' + rsAstralRange + rsBreakRange + rsDigits + rsDingbatRange + rsLowerRange + rsUpperRange + ']',
        rsFitz = '\\ud83c[\\udffb-\\udfff]',
        rsModifier = '(?:' + rsCombo + '|' + rsFitz + ')',
        rsNonAstral = '[^' + rsAstralRange + ']',
        rsRegional = '(?:\\ud83c[\\udde6-\\uddff]){2}',
        rsSurrPair = '[\\ud800-\\udbff][\\udc00-\\udfff]',
        rsUpper = '[' + rsUpperRange + ']',
        rsZWJ = '\\u200d';
    
    /** Used to compose unicode regexes. */
    var rsMiscLower = '(?:' + rsLower + '|' + rsMisc + ')',
        rsMiscUpper = '(?:' + rsUpper + '|' + rsMisc + ')',
        rsOptContrLower = '(?:' + rsApos + '(?:d|ll|m|re|s|t|ve))?',
        rsOptContrUpper = '(?:' + rsApos + '(?:D|LL|M|RE|S|T|VE))?',
        reOptMod = rsModifier + '?',
        rsOptVar = '[' + rsVarRange + ']?',
        rsOptJoin = '(?:' + rsZWJ + '(?:' + [rsNonAstral, rsRegional, rsSurrPair].join('|') + ')' + rsOptVar + reOptMod + ')*',
        rsOrdLower = '\\d*(?:1st|2nd|3rd|(?![123])\\dth)(?=\\b|[A-Z_])',
        rsOrdUpper = '\\d*(?:1ST|2ND|3RD|(?![123])\\dTH)(?=\\b|[a-z_])',
        rsSeq = rsOptVar + reOptMod + rsOptJoin,
        rsEmoji = '(?:' + [rsDingbat, rsRegional, rsSurrPair].join('|') + ')' + rsSeq;
    
    /** Used to match complex or compound words. */
    var reUnicodeWord = RegExp([
      rsUpper + '?' + rsLower + '+' + rsOptContrLower + '(?=' + [rsBreak, rsUpper, '$'].join('|') + ')',
      rsMiscUpper + '+' + rsOptContrUpper + '(?=' + [rsBreak, rsUpper + rsMiscLower, '$'].join('|') + ')',
      rsUpper + '?' + rsMiscLower + '+' + rsOptContrLower,
      rsUpper + '+' + rsOptContrUpper,
      rsOrdUpper,
      rsOrdLower,
      rsDigits,
      rsEmoji
    ].join('|'), 'g');
    
    /**
     * Splits a Unicode `string` into an array of its words.
     *
     * @private
     * @param {string} The string to inspect.
     * @returns {Array} Returns the words of `string`.
     */
    function unicodeWords(string) {
      return string.match(reUnicodeWord) || [];
    }
    
    module.exports = unicodeWords;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/camelCase.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/camelCase.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var capitalize = __webpack_require__(/*! ./capitalize */ "./node_modules/lodash/capitalize.js"),
        createCompounder = __webpack_require__(/*! ./_createCompounder */ "./node_modules/lodash/_createCompounder.js");
    
    /**
     * Converts `string` to [camel case](https://en.wikipedia.org/wiki/CamelCase).
     *
     * @static
     * @memberOf _
     * @since 3.0.0
     * @category String
     * @param {string} [string=''] The string to convert.
     * @returns {string} Returns the camel cased string.
     * @example
     *
     * _.camelCase('Foo Bar');
     * // => 'fooBar'
     *
     * _.camelCase('--foo-bar--');
     * // => 'fooBar'
     *
     * _.camelCase('__FOO_BAR__');
     * // => 'fooBar'
     */
    var camelCase = createCompounder(function(result, word, index) {
      word = word.toLowerCase();
      return result + (index ? capitalize(word) : word);
    });
    
    module.exports = camelCase;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/capitalize.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/capitalize.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var toString = __webpack_require__(/*! ./toString */ "./node_modules/lodash/toString.js"),
        upperFirst = __webpack_require__(/*! ./upperFirst */ "./node_modules/lodash/upperFirst.js");
    
    /**
     * Converts the first character of `string` to upper case and the remaining
     * to lower case.
     *
     * @static
     * @memberOf _
     * @since 3.0.0
     * @category String
     * @param {string} [string=''] The string to capitalize.
     * @returns {string} Returns the capitalized string.
     * @example
     *
     * _.capitalize('FRED');
     * // => 'Fred'
     */
    function capitalize(string) {
      return upperFirst(toString(string).toLowerCase());
    }
    
    module.exports = capitalize;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/constant.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/constant.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Creates a function that returns `value`.
     *
     * @static
     * @memberOf _
     * @since 2.4.0
     * @category Util
     * @param {*} value The value to return from the new function.
     * @returns {Function} Returns the new constant function.
     * @example
     *
     * var objects = _.times(2, _.constant({ 'a': 1 }));
     *
     * console.log(objects);
     * // => [{ 'a': 1 }, { 'a': 1 }]
     *
     * console.log(objects[0] === objects[1]);
     * // => true
     */
    function constant(value) {
      return function() {
        return value;
      };
    }
    
    module.exports = constant;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/deburr.js":
    /*!***************************************!*\
      !*** ./node_modules/lodash/deburr.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var deburrLetter = __webpack_require__(/*! ./_deburrLetter */ "./node_modules/lodash/_deburrLetter.js"),
        toString = __webpack_require__(/*! ./toString */ "./node_modules/lodash/toString.js");
    
    /** Used to match Latin Unicode letters (excluding mathematical operators). */
    var reLatin = /[\xc0-\xd6\xd8-\xf6\xf8-\xff\u0100-\u017f]/g;
    
    /** Used to compose unicode character classes. */
    var rsComboMarksRange = '\\u0300-\\u036f',
        reComboHalfMarksRange = '\\ufe20-\\ufe2f',
        rsComboSymbolsRange = '\\u20d0-\\u20ff',
        rsComboRange = rsComboMarksRange + reComboHalfMarksRange + rsComboSymbolsRange;
    
    /** Used to compose unicode capture groups. */
    var rsCombo = '[' + rsComboRange + ']';
    
    /**
     * Used to match [combining diacritical marks](https://en.wikipedia.org/wiki/Combining_Diacritical_Marks) and
     * [combining diacritical marks for symbols](https://en.wikipedia.org/wiki/Combining_Diacritical_Marks_for_Symbols).
     */
    var reComboMark = RegExp(rsCombo, 'g');
    
    /**
     * Deburrs `string` by converting
     * [Latin-1 Supplement](https://en.wikipedia.org/wiki/Latin-1_Supplement_(Unicode_block)#Character_table)
     * and [Latin Extended-A](https://en.wikipedia.org/wiki/Latin_Extended-A)
     * letters to basic Latin letters and removing
     * [combining diacritical marks](https://en.wikipedia.org/wiki/Combining_Diacritical_Marks).
     *
     * @static
     * @memberOf _
     * @since 3.0.0
     * @category String
     * @param {string} [string=''] The string to deburr.
     * @returns {string} Returns the deburred string.
     * @example
     *
     * _.deburr('déjà vu');
     * // => 'deja vu'
     */
    function deburr(string) {
      string = toString(string);
      return string && string.replace(reLatin, deburrLetter).replace(reComboMark, '');
    }
    
    module.exports = deburr;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/defaults.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/defaults.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseRest = __webpack_require__(/*! ./_baseRest */ "./node_modules/lodash/_baseRest.js"),
        eq = __webpack_require__(/*! ./eq */ "./node_modules/lodash/eq.js"),
        isIterateeCall = __webpack_require__(/*! ./_isIterateeCall */ "./node_modules/lodash/_isIterateeCall.js"),
        keysIn = __webpack_require__(/*! ./keysIn */ "./node_modules/lodash/keysIn.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /**
     * Assigns own and inherited enumerable string keyed properties of source
     * objects to the destination object for all destination properties that
     * resolve to `undefined`. Source objects are applied from left to right.
     * Once a property is set, additional values of the same property are ignored.
     *
     * **Note:** This method mutates `object`.
     *
     * @static
     * @since 0.1.0
     * @memberOf _
     * @category Object
     * @param {Object} object The destination object.
     * @param {...Object} [sources] The source objects.
     * @returns {Object} Returns `object`.
     * @see _.defaultsDeep
     * @example
     *
     * _.defaults({ 'a': 1 }, { 'b': 2 }, { 'a': 3 });
     * // => { 'a': 1, 'b': 2 }
     */
    var defaults = baseRest(function(object, sources) {
      object = Object(object);
    
      var index = -1;
      var length = sources.length;
      var guard = length > 2 ? sources[2] : undefined;
    
      if (guard && isIterateeCall(sources[0], sources[1], guard)) {
        length = 1;
      }
    
      while (++index < length) {
        var source = sources[index];
        var props = keysIn(source);
        var propsIndex = -1;
        var propsLength = props.length;
    
        while (++propsIndex < propsLength) {
          var key = props[propsIndex];
          var value = object[key];
    
          if (value === undefined ||
              (eq(value, objectProto[key]) && !hasOwnProperty.call(object, key))) {
            object[key] = source[key];
          }
        }
      }
    
      return object;
    });
    
    module.exports = defaults;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/eq.js":
    /*!***********************************!*\
      !*** ./node_modules/lodash/eq.js ***!
      \***********************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Performs a
     * [`SameValueZero`](http://ecma-international.org/ecma-262/7.0/#sec-samevaluezero)
     * comparison between two values to determine if they are equivalent.
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category Lang
     * @param {*} value The value to compare.
     * @param {*} other The other value to compare.
     * @returns {boolean} Returns `true` if the values are equivalent, else `false`.
     * @example
     *
     * var object = { 'a': 1 };
     * var other = { 'a': 1 };
     *
     * _.eq(object, object);
     * // => true
     *
     * _.eq(object, other);
     * // => false
     *
     * _.eq('a', 'a');
     * // => true
     *
     * _.eq('a', Object('a'));
     * // => false
     *
     * _.eq(NaN, NaN);
     * // => true
     */
    function eq(value, other) {
      return value === other || (value !== value && other !== other);
    }
    
    module.exports = eq;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/filter.js":
    /*!***************************************!*\
      !*** ./node_modules/lodash/filter.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var arrayFilter = __webpack_require__(/*! ./_arrayFilter */ "./node_modules/lodash/_arrayFilter.js"),
        baseFilter = __webpack_require__(/*! ./_baseFilter */ "./node_modules/lodash/_baseFilter.js"),
        baseIteratee = __webpack_require__(/*! ./_baseIteratee */ "./node_modules/lodash/_baseIteratee.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js");
    
    /**
     * Iterates over elements of `collection`, returning an array of all elements
     * `predicate` returns truthy for. The predicate is invoked with three
     * arguments: (value, index|key, collection).
     *
     * **Note:** Unlike `_.remove`, this method returns a new array.
     *
     * @static
     * @memberOf _
     * @since 0.1.0
     * @category Collection
     * @param {Array|Object} collection The collection to iterate over.
     * @param {Function} [predicate=_.identity] The function invoked per iteration.
     * @returns {Array} Returns the new filtered array.
     * @see _.reject
     * @example
     *
     * var users = [
     *   { 'user': 'barney', 'age': 36, 'active': true },
     *   { 'user': 'fred',   'age': 40, 'active': false }
     * ];
     *
     * _.filter(users, function(o) { return !o.active; });
     * // => objects for ['fred']
     *
     * // The `_.matches` iteratee shorthand.
     * _.filter(users, { 'age': 36, 'active': true });
     * // => objects for ['barney']
     *
     * // The `_.matchesProperty` iteratee shorthand.
     * _.filter(users, ['active', false]);
     * // => objects for ['fred']
     *
     * // The `_.property` iteratee shorthand.
     * _.filter(users, 'active');
     * // => objects for ['barney']
     *
     * // Combining several predicates using `_.overEvery` or `_.overSome`.
     * _.filter(users, _.overSome([{ 'age': 36 }, ['age', 40]]));
     * // => objects for ['fred', 'barney']
     */
    function filter(collection, predicate) {
      var func = isArray(collection) ? arrayFilter : baseFilter;
      return func(collection, baseIteratee(predicate, 3));
    }
    
    module.exports = filter;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/forEach.js":
    /*!****************************************!*\
      !*** ./node_modules/lodash/forEach.js ***!
      \****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var arrayEach = __webpack_require__(/*! ./_arrayEach */ "./node_modules/lodash/_arrayEach.js"),
        baseEach = __webpack_require__(/*! ./_baseEach */ "./node_modules/lodash/_baseEach.js"),
        castFunction = __webpack_require__(/*! ./_castFunction */ "./node_modules/lodash/_castFunction.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js");
    
    /**
     * Iterates over elements of `collection` and invokes `iteratee` for each element.
     * The iteratee is invoked with three arguments: (value, index|key, collection).
     * Iteratee functions may exit iteration early by explicitly returning `false`.
     *
     * **Note:** As with other "Collections" methods, objects with a "length"
     * property are iterated like arrays. To avoid this behavior use `_.forIn`
     * or `_.forOwn` for object iteration.
     *
     * @static
     * @memberOf _
     * @since 0.1.0
     * @alias each
     * @category Collection
     * @param {Array|Object} collection The collection to iterate over.
     * @param {Function} [iteratee=_.identity] The function invoked per iteration.
     * @returns {Array|Object} Returns `collection`.
     * @see _.forEachRight
     * @example
     *
     * _.forEach([1, 2], function(value) {
     *   console.log(value);
     * });
     * // => Logs `1` then `2`.
     *
     * _.forEach({ 'a': 1, 'b': 2 }, function(value, key) {
     *   console.log(key);
     * });
     * // => Logs 'a' then 'b' (iteration order is not guaranteed).
     */
    function forEach(collection, iteratee) {
      var func = isArray(collection) ? arrayEach : baseEach;
      return func(collection, castFunction(iteratee));
    }
    
    module.exports = forEach;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/get.js":
    /*!************************************!*\
      !*** ./node_modules/lodash/get.js ***!
      \************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGet = __webpack_require__(/*! ./_baseGet */ "./node_modules/lodash/_baseGet.js");
    
    /**
     * Gets the value at `path` of `object`. If the resolved value is
     * `undefined`, the `defaultValue` is returned in its place.
     *
     * @static
     * @memberOf _
     * @since 3.7.0
     * @category Object
     * @param {Object} object The object to query.
     * @param {Array|string} path The path of the property to get.
     * @param {*} [defaultValue] The value returned for `undefined` resolved values.
     * @returns {*} Returns the resolved value.
     * @example
     *
     * var object = { 'a': [{ 'b': { 'c': 3 } }] };
     *
     * _.get(object, 'a[0].b.c');
     * // => 3
     *
     * _.get(object, ['a', '0', 'b', 'c']);
     * // => 3
     *
     * _.get(object, 'a.b.c', 'default');
     * // => 'default'
     */
    function get(object, path, defaultValue) {
      var result = object == null ? undefined : baseGet(object, path);
      return result === undefined ? defaultValue : result;
    }
    
    module.exports = get;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/hasIn.js":
    /*!**************************************!*\
      !*** ./node_modules/lodash/hasIn.js ***!
      \**************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseHasIn = __webpack_require__(/*! ./_baseHasIn */ "./node_modules/lodash/_baseHasIn.js"),
        hasPath = __webpack_require__(/*! ./_hasPath */ "./node_modules/lodash/_hasPath.js");
    
    /**
     * Checks if `path` is a direct or inherited property of `object`.
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category Object
     * @param {Object} object The object to query.
     * @param {Array|string} path The path to check.
     * @returns {boolean} Returns `true` if `path` exists, else `false`.
     * @example
     *
     * var object = _.create({ 'a': _.create({ 'b': 2 }) });
     *
     * _.hasIn(object, 'a');
     * // => true
     *
     * _.hasIn(object, 'a.b');
     * // => true
     *
     * _.hasIn(object, ['a', 'b']);
     * // => true
     *
     * _.hasIn(object, 'b');
     * // => false
     */
    function hasIn(object, path) {
      return object != null && hasPath(object, path, baseHasIn);
    }
    
    module.exports = hasIn;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/identity.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/identity.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * This method returns the first argument it receives.
     *
     * @static
     * @since 0.1.0
     * @memberOf _
     * @category Util
     * @param {*} value Any value.
     * @returns {*} Returns `value`.
     * @example
     *
     * var object = { 'a': 1 };
     *
     * console.log(_.identity(object) === object);
     * // => true
     */
    function identity(value) {
      return value;
    }
    
    module.exports = identity;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isArguments.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/isArguments.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseIsArguments = __webpack_require__(/*! ./_baseIsArguments */ "./node_modules/lodash/_baseIsArguments.js"),
        isObjectLike = __webpack_require__(/*! ./isObjectLike */ "./node_modules/lodash/isObjectLike.js");
    
    /** Used for built-in method references. */
    var objectProto = Object.prototype;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /** Built-in value references. */
    var propertyIsEnumerable = objectProto.propertyIsEnumerable;
    
    /**
     * Checks if `value` is likely an `arguments` object.
     *
     * @static
     * @memberOf _
     * @since 0.1.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is an `arguments` object,
     *  else `false`.
     * @example
     *
     * _.isArguments(function() { return arguments; }());
     * // => true
     *
     * _.isArguments([1, 2, 3]);
     * // => false
     */
    var isArguments = baseIsArguments(function() { return arguments; }()) ? baseIsArguments : function(value) {
      return isObjectLike(value) && hasOwnProperty.call(value, 'callee') &&
        !propertyIsEnumerable.call(value, 'callee');
    };
    
    module.exports = isArguments;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isArray.js":
    /*!****************************************!*\
      !*** ./node_modules/lodash/isArray.js ***!
      \****************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if `value` is classified as an `Array` object.
     *
     * @static
     * @memberOf _
     * @since 0.1.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is an array, else `false`.
     * @example
     *
     * _.isArray([1, 2, 3]);
     * // => true
     *
     * _.isArray(document.body.children);
     * // => false
     *
     * _.isArray('abc');
     * // => false
     *
     * _.isArray(_.noop);
     * // => false
     */
    var isArray = Array.isArray;
    
    module.exports = isArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isArrayLike.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/isArrayLike.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var isFunction = __webpack_require__(/*! ./isFunction */ "./node_modules/lodash/isFunction.js"),
        isLength = __webpack_require__(/*! ./isLength */ "./node_modules/lodash/isLength.js");
    
    /**
     * Checks if `value` is array-like. A value is considered array-like if it's
     * not a function and has a `value.length` that's an integer greater than or
     * equal to `0` and less than or equal to `Number.MAX_SAFE_INTEGER`.
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is array-like, else `false`.
     * @example
     *
     * _.isArrayLike([1, 2, 3]);
     * // => true
     *
     * _.isArrayLike(document.body.children);
     * // => true
     *
     * _.isArrayLike('abc');
     * // => true
     *
     * _.isArrayLike(_.noop);
     * // => false
     */
    function isArrayLike(value) {
      return value != null && isLength(value.length) && !isFunction(value);
    }
    
    module.exports = isArrayLike;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isBuffer.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/isBuffer.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    /* WEBPACK VAR INJECTION */(function(module) {var root = __webpack_require__(/*! ./_root */ "./node_modules/lodash/_root.js"),
        stubFalse = __webpack_require__(/*! ./stubFalse */ "./node_modules/lodash/stubFalse.js");
    
    /** Detect free variable `exports`. */
    var freeExports =  true && exports && !exports.nodeType && exports;
    
    /** Detect free variable `module`. */
    var freeModule = freeExports && typeof module == 'object' && module && !module.nodeType && module;
    
    /** Detect the popular CommonJS extension `module.exports`. */
    var moduleExports = freeModule && freeModule.exports === freeExports;
    
    /** Built-in value references. */
    var Buffer = moduleExports ? root.Buffer : undefined;
    
    /* Built-in method references for those with the same name as other `lodash` methods. */
    var nativeIsBuffer = Buffer ? Buffer.isBuffer : undefined;
    
    /**
     * Checks if `value` is a buffer.
     *
     * @static
     * @memberOf _
     * @since 4.3.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a buffer, else `false`.
     * @example
     *
     * _.isBuffer(new Buffer(2));
     * // => true
     *
     * _.isBuffer(new Uint8Array(2));
     * // => false
     */
    var isBuffer = nativeIsBuffer || stubFalse;
    
    module.exports = isBuffer;
    
    /* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./../webpack/buildin/module.js */ "./node_modules/webpack/buildin/module.js")(module)))
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isFunction.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/isFunction.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ "./node_modules/lodash/_baseGetTag.js"),
        isObject = __webpack_require__(/*! ./isObject */ "./node_modules/lodash/isObject.js");
    
    /** `Object#toString` result references. */
    var asyncTag = '[object AsyncFunction]',
        funcTag = '[object Function]',
        genTag = '[object GeneratorFunction]',
        proxyTag = '[object Proxy]';
    
    /**
     * Checks if `value` is classified as a `Function` object.
     *
     * @static
     * @memberOf _
     * @since 0.1.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a function, else `false`.
     * @example
     *
     * _.isFunction(_);
     * // => true
     *
     * _.isFunction(/abc/);
     * // => false
     */
    function isFunction(value) {
      if (!isObject(value)) {
        return false;
      }
      // The use of `Object#toString` avoids issues with the `typeof` operator
      // in Safari 9 which returns 'object' for typed arrays and other constructors.
      var tag = baseGetTag(value);
      return tag == funcTag || tag == genTag || tag == asyncTag || tag == proxyTag;
    }
    
    module.exports = isFunction;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isLength.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/isLength.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /** Used as references for various `Number` constants. */
    var MAX_SAFE_INTEGER = 9007199254740991;
    
    /**
     * Checks if `value` is a valid array-like length.
     *
     * **Note:** This method is loosely based on
     * [`ToLength`](http://ecma-international.org/ecma-262/7.0/#sec-tolength).
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a valid length, else `false`.
     * @example
     *
     * _.isLength(3);
     * // => true
     *
     * _.isLength(Number.MIN_VALUE);
     * // => false
     *
     * _.isLength(Infinity);
     * // => false
     *
     * _.isLength('3');
     * // => false
     */
    function isLength(value) {
      return typeof value == 'number' &&
        value > -1 && value % 1 == 0 && value <= MAX_SAFE_INTEGER;
    }
    
    module.exports = isLength;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isObject.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/isObject.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if `value` is the
     * [language type](http://www.ecma-international.org/ecma-262/7.0/#sec-ecmascript-language-types)
     * of `Object`. (e.g. arrays, functions, objects, regexes, `new Number(0)`, and `new String('')`)
     *
     * @static
     * @memberOf _
     * @since 0.1.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is an object, else `false`.
     * @example
     *
     * _.isObject({});
     * // => true
     *
     * _.isObject([1, 2, 3]);
     * // => true
     *
     * _.isObject(_.noop);
     * // => true
     *
     * _.isObject(null);
     * // => false
     */
    function isObject(value) {
      var type = typeof value;
      return value != null && (type == 'object' || type == 'function');
    }
    
    module.exports = isObject;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isObjectLike.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/isObjectLike.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if `value` is object-like. A value is object-like if it's not `null`
     * and has a `typeof` result of "object".
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is object-like, else `false`.
     * @example
     *
     * _.isObjectLike({});
     * // => true
     *
     * _.isObjectLike([1, 2, 3]);
     * // => true
     *
     * _.isObjectLike(_.noop);
     * // => false
     *
     * _.isObjectLike(null);
     * // => false
     */
    function isObjectLike(value) {
      return value != null && typeof value == 'object';
    }
    
    module.exports = isObjectLike;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isPlainObject.js":
    /*!**********************************************!*\
      !*** ./node_modules/lodash/isPlainObject.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ "./node_modules/lodash/_baseGetTag.js"),
        getPrototype = __webpack_require__(/*! ./_getPrototype */ "./node_modules/lodash/_getPrototype.js"),
        isObjectLike = __webpack_require__(/*! ./isObjectLike */ "./node_modules/lodash/isObjectLike.js");
    
    /** `Object#toString` result references. */
    var objectTag = '[object Object]';
    
    /** Used for built-in method references. */
    var funcProto = Function.prototype,
        objectProto = Object.prototype;
    
    /** Used to resolve the decompiled source of functions. */
    var funcToString = funcProto.toString;
    
    /** Used to check objects for own properties. */
    var hasOwnProperty = objectProto.hasOwnProperty;
    
    /** Used to infer the `Object` constructor. */
    var objectCtorString = funcToString.call(Object);
    
    /**
     * Checks if `value` is a plain object, that is, an object created by the
     * `Object` constructor or one with a `[[Prototype]]` of `null`.
     *
     * @static
     * @memberOf _
     * @since 0.8.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a plain object, else `false`.
     * @example
     *
     * function Foo() {
     *   this.a = 1;
     * }
     *
     * _.isPlainObject(new Foo);
     * // => false
     *
     * _.isPlainObject([1, 2, 3]);
     * // => false
     *
     * _.isPlainObject({ 'x': 0, 'y': 0 });
     * // => true
     *
     * _.isPlainObject(Object.create(null));
     * // => true
     */
    function isPlainObject(value) {
      if (!isObjectLike(value) || baseGetTag(value) != objectTag) {
        return false;
      }
      var proto = getPrototype(value);
      if (proto === null) {
        return true;
      }
      var Ctor = hasOwnProperty.call(proto, 'constructor') && proto.constructor;
      return typeof Ctor == 'function' && Ctor instanceof Ctor &&
        funcToString.call(Ctor) == objectCtorString;
    }
    
    module.exports = isPlainObject;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isString.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/isString.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ "./node_modules/lodash/_baseGetTag.js"),
        isArray = __webpack_require__(/*! ./isArray */ "./node_modules/lodash/isArray.js"),
        isObjectLike = __webpack_require__(/*! ./isObjectLike */ "./node_modules/lodash/isObjectLike.js");
    
    /** `Object#toString` result references. */
    var stringTag = '[object String]';
    
    /**
     * Checks if `value` is classified as a `String` primitive or object.
     *
     * @static
     * @since 0.1.0
     * @memberOf _
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a string, else `false`.
     * @example
     *
     * _.isString('abc');
     * // => true
     *
     * _.isString(1);
     * // => false
     */
    function isString(value) {
      return typeof value == 'string' ||
        (!isArray(value) && isObjectLike(value) && baseGetTag(value) == stringTag);
    }
    
    module.exports = isString;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isSymbol.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/isSymbol.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseGetTag = __webpack_require__(/*! ./_baseGetTag */ "./node_modules/lodash/_baseGetTag.js"),
        isObjectLike = __webpack_require__(/*! ./isObjectLike */ "./node_modules/lodash/isObjectLike.js");
    
    /** `Object#toString` result references. */
    var symbolTag = '[object Symbol]';
    
    /**
     * Checks if `value` is classified as a `Symbol` primitive or object.
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a symbol, else `false`.
     * @example
     *
     * _.isSymbol(Symbol.iterator);
     * // => true
     *
     * _.isSymbol('abc');
     * // => false
     */
    function isSymbol(value) {
      return typeof value == 'symbol' ||
        (isObjectLike(value) && baseGetTag(value) == symbolTag);
    }
    
    module.exports = isSymbol;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isTypedArray.js":
    /*!*********************************************!*\
      !*** ./node_modules/lodash/isTypedArray.js ***!
      \*********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseIsTypedArray = __webpack_require__(/*! ./_baseIsTypedArray */ "./node_modules/lodash/_baseIsTypedArray.js"),
        baseUnary = __webpack_require__(/*! ./_baseUnary */ "./node_modules/lodash/_baseUnary.js"),
        nodeUtil = __webpack_require__(/*! ./_nodeUtil */ "./node_modules/lodash/_nodeUtil.js");
    
    /* Node.js helper references. */
    var nodeIsTypedArray = nodeUtil && nodeUtil.isTypedArray;
    
    /**
     * Checks if `value` is classified as a typed array.
     *
     * @static
     * @memberOf _
     * @since 3.0.0
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is a typed array, else `false`.
     * @example
     *
     * _.isTypedArray(new Uint8Array);
     * // => true
     *
     * _.isTypedArray([]);
     * // => false
     */
    var isTypedArray = nodeIsTypedArray ? baseUnary(nodeIsTypedArray) : baseIsTypedArray;
    
    module.exports = isTypedArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/isUndefined.js":
    /*!********************************************!*\
      !*** ./node_modules/lodash/isUndefined.js ***!
      \********************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * Checks if `value` is `undefined`.
     *
     * @static
     * @since 0.1.0
     * @memberOf _
     * @category Lang
     * @param {*} value The value to check.
     * @returns {boolean} Returns `true` if `value` is `undefined`, else `false`.
     * @example
     *
     * _.isUndefined(void 0);
     * // => true
     *
     * _.isUndefined(null);
     * // => false
     */
    function isUndefined(value) {
      return value === undefined;
    }
    
    module.exports = isUndefined;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/keys.js":
    /*!*************************************!*\
      !*** ./node_modules/lodash/keys.js ***!
      \*************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var arrayLikeKeys = __webpack_require__(/*! ./_arrayLikeKeys */ "./node_modules/lodash/_arrayLikeKeys.js"),
        baseKeys = __webpack_require__(/*! ./_baseKeys */ "./node_modules/lodash/_baseKeys.js"),
        isArrayLike = __webpack_require__(/*! ./isArrayLike */ "./node_modules/lodash/isArrayLike.js");
    
    /**
     * Creates an array of the own enumerable property names of `object`.
     *
     * **Note:** Non-object values are coerced to objects. See the
     * [ES spec](http://ecma-international.org/ecma-262/7.0/#sec-object.keys)
     * for more details.
     *
     * @static
     * @since 0.1.0
     * @memberOf _
     * @category Object
     * @param {Object} object The object to query.
     * @returns {Array} Returns the array of property names.
     * @example
     *
     * function Foo() {
     *   this.a = 1;
     *   this.b = 2;
     * }
     *
     * Foo.prototype.c = 3;
     *
     * _.keys(new Foo);
     * // => ['a', 'b'] (iteration order is not guaranteed)
     *
     * _.keys('hi');
     * // => ['0', '1']
     */
    function keys(object) {
      return isArrayLike(object) ? arrayLikeKeys(object) : baseKeys(object);
    }
    
    module.exports = keys;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/keysIn.js":
    /*!***************************************!*\
      !*** ./node_modules/lodash/keysIn.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var arrayLikeKeys = __webpack_require__(/*! ./_arrayLikeKeys */ "./node_modules/lodash/_arrayLikeKeys.js"),
        baseKeysIn = __webpack_require__(/*! ./_baseKeysIn */ "./node_modules/lodash/_baseKeysIn.js"),
        isArrayLike = __webpack_require__(/*! ./isArrayLike */ "./node_modules/lodash/isArrayLike.js");
    
    /**
     * Creates an array of the own and inherited enumerable property names of `object`.
     *
     * **Note:** Non-object values are coerced to objects.
     *
     * @static
     * @memberOf _
     * @since 3.0.0
     * @category Object
     * @param {Object} object The object to query.
     * @returns {Array} Returns the array of property names.
     * @example
     *
     * function Foo() {
     *   this.a = 1;
     *   this.b = 2;
     * }
     *
     * Foo.prototype.c = 3;
     *
     * _.keysIn(new Foo);
     * // => ['a', 'b', 'c'] (iteration order is not guaranteed)
     */
    function keysIn(object) {
      return isArrayLike(object) ? arrayLikeKeys(object, true) : baseKeysIn(object);
    }
    
    module.exports = keysIn;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/memoize.js":
    /*!****************************************!*\
      !*** ./node_modules/lodash/memoize.js ***!
      \****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var MapCache = __webpack_require__(/*! ./_MapCache */ "./node_modules/lodash/_MapCache.js");
    
    /** Error message constants. */
    var FUNC_ERROR_TEXT = 'Expected a function';
    
    /**
     * Creates a function that memoizes the result of `func`. If `resolver` is
     * provided, it determines the cache key for storing the result based on the
     * arguments provided to the memoized function. By default, the first argument
     * provided to the memoized function is used as the map cache key. The `func`
     * is invoked with the `this` binding of the memoized function.
     *
     * **Note:** The cache is exposed as the `cache` property on the memoized
     * function. Its creation may be customized by replacing the `_.memoize.Cache`
     * constructor with one whose instances implement the
     * [`Map`](http://ecma-international.org/ecma-262/7.0/#sec-properties-of-the-map-prototype-object)
     * method interface of `clear`, `delete`, `get`, `has`, and `set`.
     *
     * @static
     * @memberOf _
     * @since 0.1.0
     * @category Function
     * @param {Function} func The function to have its output memoized.
     * @param {Function} [resolver] The function to resolve the cache key.
     * @returns {Function} Returns the new memoized function.
     * @example
     *
     * var object = { 'a': 1, 'b': 2 };
     * var other = { 'c': 3, 'd': 4 };
     *
     * var values = _.memoize(_.values);
     * values(object);
     * // => [1, 2]
     *
     * values(other);
     * // => [3, 4]
     *
     * object.a = 2;
     * values(object);
     * // => [1, 2]
     *
     * // Modify the result cache.
     * values.cache.set(object, ['a', 'b']);
     * values(object);
     * // => ['a', 'b']
     *
     * // Replace `_.memoize.Cache`.
     * _.memoize.Cache = WeakMap;
     */
    function memoize(func, resolver) {
      if (typeof func != 'function' || (resolver != null && typeof resolver != 'function')) {
        throw new TypeError(FUNC_ERROR_TEXT);
      }
      var memoized = function() {
        var args = arguments,
            key = resolver ? resolver.apply(this, args) : args[0],
            cache = memoized.cache;
    
        if (cache.has(key)) {
          return cache.get(key);
        }
        var result = func.apply(this, args);
        memoized.cache = cache.set(key, result) || cache;
        return result;
      };
      memoized.cache = new (memoize.Cache || MapCache);
      return memoized;
    }
    
    // Expose `MapCache`.
    memoize.Cache = MapCache;
    
    module.exports = memoize;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/property.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/property.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseProperty = __webpack_require__(/*! ./_baseProperty */ "./node_modules/lodash/_baseProperty.js"),
        basePropertyDeep = __webpack_require__(/*! ./_basePropertyDeep */ "./node_modules/lodash/_basePropertyDeep.js"),
        isKey = __webpack_require__(/*! ./_isKey */ "./node_modules/lodash/_isKey.js"),
        toKey = __webpack_require__(/*! ./_toKey */ "./node_modules/lodash/_toKey.js");
    
    /**
     * Creates a function that returns the value at `path` of a given object.
     *
     * @static
     * @memberOf _
     * @since 2.4.0
     * @category Util
     * @param {Array|string} path The path of the property to get.
     * @returns {Function} Returns the new accessor function.
     * @example
     *
     * var objects = [
     *   { 'a': { 'b': 2 } },
     *   { 'a': { 'b': 1 } }
     * ];
     *
     * _.map(objects, _.property('a.b'));
     * // => [2, 1]
     *
     * _.map(_.sortBy(objects, _.property(['a', 'b'])), 'a.b');
     * // => [1, 2]
     */
    function property(path) {
      return isKey(path) ? baseProperty(toKey(path)) : basePropertyDeep(path);
    }
    
    module.exports = property;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/stubArray.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/stubArray.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * This method returns a new empty array.
     *
     * @static
     * @memberOf _
     * @since 4.13.0
     * @category Util
     * @returns {Array} Returns the new empty array.
     * @example
     *
     * var arrays = _.times(2, _.stubArray);
     *
     * console.log(arrays);
     * // => [[], []]
     *
     * console.log(arrays[0] === arrays[1]);
     * // => false
     */
    function stubArray() {
      return [];
    }
    
    module.exports = stubArray;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/stubFalse.js":
    /*!******************************************!*\
      !*** ./node_modules/lodash/stubFalse.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /**
     * This method returns `false`.
     *
     * @static
     * @memberOf _
     * @since 4.13.0
     * @category Util
     * @returns {boolean} Returns `false`.
     * @example
     *
     * _.times(2, _.stubFalse);
     * // => [false, false]
     */
    function stubFalse() {
      return false;
    }
    
    module.exports = stubFalse;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/toString.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/toString.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var baseToString = __webpack_require__(/*! ./_baseToString */ "./node_modules/lodash/_baseToString.js");
    
    /**
     * Converts `value` to a string. An empty string is returned for `null`
     * and `undefined` values. The sign of `-0` is preserved.
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category Lang
     * @param {*} value The value to convert.
     * @returns {string} Returns the converted string.
     * @example
     *
     * _.toString(null);
     * // => ''
     *
     * _.toString(-0);
     * // => '-0'
     *
     * _.toString([1, 2, 3]);
     * // => '1,2,3'
     */
    function toString(value) {
      return value == null ? '' : baseToString(value);
    }
    
    module.exports = toString;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/uniqueId.js":
    /*!*****************************************!*\
      !*** ./node_modules/lodash/uniqueId.js ***!
      \*****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var toString = __webpack_require__(/*! ./toString */ "./node_modules/lodash/toString.js");
    
    /** Used to generate unique IDs. */
    var idCounter = 0;
    
    /**
     * Generates a unique ID. If `prefix` is given, the ID is appended to it.
     *
     * @static
     * @since 0.1.0
     * @memberOf _
     * @category Util
     * @param {string} [prefix=''] The value to prefix the ID with.
     * @returns {string} Returns the unique ID.
     * @example
     *
     * _.uniqueId('contact_');
     * // => 'contact_104'
     *
     * _.uniqueId();
     * // => '105'
     */
    function uniqueId(prefix) {
      var id = ++idCounter;
      return toString(prefix) + id;
    }
    
    module.exports = uniqueId;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/upperFirst.js":
    /*!*******************************************!*\
      !*** ./node_modules/lodash/upperFirst.js ***!
      \*******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var createCaseFirst = __webpack_require__(/*! ./_createCaseFirst */ "./node_modules/lodash/_createCaseFirst.js");
    
    /**
     * Converts the first character of `string` to upper case.
     *
     * @static
     * @memberOf _
     * @since 4.0.0
     * @category String
     * @param {string} [string=''] The string to convert.
     * @returns {string} Returns the converted string.
     * @example
     *
     * _.upperFirst('fred');
     * // => 'Fred'
     *
     * _.upperFirst('FRED');
     * // => 'FRED'
     */
    var upperFirst = createCaseFirst('toUpperCase');
    
    module.exports = upperFirst;
    
    
    /***/ }),
    
    /***/ "./node_modules/lodash/words.js":
    /*!**************************************!*\
      !*** ./node_modules/lodash/words.js ***!
      \**************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    var asciiWords = __webpack_require__(/*! ./_asciiWords */ "./node_modules/lodash/_asciiWords.js"),
        hasUnicodeWord = __webpack_require__(/*! ./_hasUnicodeWord */ "./node_modules/lodash/_hasUnicodeWord.js"),
        toString = __webpack_require__(/*! ./toString */ "./node_modules/lodash/toString.js"),
        unicodeWords = __webpack_require__(/*! ./_unicodeWords */ "./node_modules/lodash/_unicodeWords.js");
    
    /**
     * Splits `string` into an array of its words.
     *
     * @static
     * @memberOf _
     * @since 3.0.0
     * @category String
     * @param {string} [string=''] The string to inspect.
     * @param {RegExp|string} [pattern] The pattern to match words.
     * @param- {Object} [guard] Enables use as an iteratee for methods like `_.map`.
     * @returns {Array} Returns the words of `string`.
     * @example
     *
     * _.words('fred, barney, & pebbles');
     * // => ['fred', 'barney', 'pebbles']
     *
     * _.words('fred, barney, & pebbles', /[^, ]+/g);
     * // => ['fred', 'barney', '&', 'pebbles']
     */
    function words(string, pattern, guard) {
      string = toString(string);
      pattern = guard ? undefined : pattern;
    
      if (pattern === undefined) {
        return hasUnicodeWord(string) ? unicodeWords(string) : asciiWords(string);
      }
      return string.match(pattern) || [];
    }
    
    module.exports = words;
    
    
    /***/ }),
    
    /***/ "./node_modules/webpack/buildin/global.js":
    /*!***********************************!*\
      !*** (webpack)/buildin/global.js ***!
      \***********************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    var g;
    
    // This works in non-strict mode
    g = (function() {
        return this;
    })();
    
    try {
        // This works if eval is allowed (see CSP)
        g = g || new Function("return this")();
    } catch (e) {
        // This works if the window reference is available
        if (typeof window === "object") g = window;
    }
    
    // g can still be undefined, but nothing to do about it...
    // We return undefined, instead of nothing here, so it's
    // easier to handle this case. if(!global) { ...}
    
    module.exports = g;
    
    
    /***/ }),
    
    /***/ "./node_modules/webpack/buildin/module.js":
    /*!***********************************!*\
      !*** (webpack)/buildin/module.js ***!
      \***********************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    module.exports = function(module) {
        if (!module.webpackPolyfill) {
            module.deprecate = function() {};
            module.paths = [];
            // module.parent = undefined by default
            if (!module.children) module.children = [];
            Object.defineProperty(module, "loaded", {
                enumerable: true,
                get: function() {
                    return module.l;
                }
            });
            Object.defineProperty(module, "id", {
                enumerable: true,
                get: function() {
                    return module.i;
                }
            });
            module.webpackPolyfill = 1;
        }
        return module;
    };
    
    
    /***/ }),
    
    /***/ "./src/hbs-app/fe-components/core-carousel/core-carousel.js":
    /*!******************************************************************!*\
      !*** ./src/hbs-app/fe-components/core-carousel/core-carousel.js ***!
      \******************************************************************/
    /*! exports provided: componentReference, styleDefinition */
    /***/ (function(module, __webpack_exports__, __webpack_require__) {
    
    "use strict";
    __webpack_require__.r(__webpack_exports__);
    /* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "componentReference", function() { return componentReference; });
    /* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "styleDefinition", function() { return styleDefinition; });
    /* harmony import */ var _brightcove_player_loader__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @brightcove/player-loader */ "./node_modules/@brightcove/player-loader/dist/brightcove-player-loader.es.js");
    /* harmony import */ var jquery__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! jquery */ "./node_modules/jquery/dist/jquery.js");
    /* harmony import */ var jquery__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(jquery__WEBPACK_IMPORTED_MODULE_1__);
    /* harmony import */ var bootstrap_js_dist_modal__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! bootstrap/js/dist/modal */ "./node_modules/bootstrap/js/dist/modal.js");
    /* harmony import */ var bootstrap_js_dist_modal__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(bootstrap_js_dist_modal__WEBPACK_IMPORTED_MODULE_2__);
    /**
     * @module {component-name}
     * @version {version}
     * @since {date}
     */
    // dependencies
    
    
    
    
    var compRegisterRef = __webpack_require__(/*! lib/component-register */ "./src/lib/component-register.js");
    
    var registerComponent = compRegisterRef.registerComponent;
    /**
     * The definition of the component. Each DOM element will
     * define the elements class with this string.
     * @type {string}
     */
    
    var componentReference = 'app-js__carousel';
    /**
     * The style definition of the component.
     * @type {string}
     */
    
    var styleDefinition = 'carousel';
    /**
     * Factory method to create an instance. Linked to an html element.
     *
     * @returns {object} Component instance.
     */
    
    function createCarouselInstance() {
      /**
       * Component instance.
       * @type {Object}
       */
      var instance = {};
      instance.classNames = {
        isImageBleed: 'isImageBleed'
      };
      /**
       * By setting `instance.domRefs` the baseComponent will replace the value
       * of each keys in `instance.domRefs.first` with single elements found
       * in `instance.element`. Same for `instance.domRefs.all`, but each key
       * will have an array of elements.
       *
       * `first` example: The value of `childEl` will be a `HTMLElement`
       * `all` example: The value of `rows` will be an `Array` of `HTMLElement`
       * @type {Object}
       */
      // instance.domRefs = {
      //   definition: styleDefinition,
      //   first: {
      //     childEl: '__child'
      //   },
      //   all: {
      //     rows: '__row'
      //   }
      // };
    
      /** @type {Object} */
      // const singleRefs = instance.domRefs.first;
    
      /** @type {Object} */
      // const listRefs = instance.domRefs.all;
    
      /**
       * `created` is called before `attached`. Can be used to pass data to
       * childrens `params` property.
       */
      // instance.created = () => {
      //   forEach(instance.children, updateChildParams);
      // };
    
      /**
       * Update the `instance.params` for `childInstance`.
       *
       * @param {object} childInstance
       */
      // function updateChildParams(childInstance) {
      //   childInstance.receiveNewParams({
      //     id: instance.attribute.id,
      //     updateId: onIdChanged
      //   });
      // }
    
      /**
       * The `instance.params` object was changed from parent calling `instance.receiveNewParams`.
       * Properties and callback functions can be dealt with or passed down to
       * child components.
       */
      // instance.onNewParamsReceived = () => {
      //   forEach(instance.children, child => {
      //     child.receiveNewParams({
      //       updateId: instance.params.updateId
      //     });
      //   });
      // };
    
      /**
       * Initialize any DOM elements which can be found within the hbs file for
       * this component.
       * @returns {void}
       */
    
      function initDOMReferences() {// myEl = domUtil.findFirst('.'
        //  .concat(exports.styleDefinition)
        //  .concat('__my-el'),
        //  instance.element
        //  );
      }
      /**
       * Alligns carousel container when breadcrumbs are present
       * @returns {void}
       */
    
    
      function allignCarouselOnBreadcrumbs() {
        console.log(instance.element);
        var imageBleedElement = document.querySelector(".hero-carousel .isImageBleed");
        var alertElement = document.querySelector(".bdb-alert:not(.show-announcement)");
        var heroCarouselContainer = document.querySelector(".hero-carousel .app-js__carousel");
        var hasMultipleAlerts = (document.querySelectorAll('.bdb-alert > .d-flex') || []).length > 1;
    
        if (alertElement) {
          jquery__WEBPACK_IMPORTED_MODULE_1___default()(alertElement).css('height', 'auto');
        }
    
        var alertHeight = alertElement ? alertElement.offsetHeight : 0;
    
        if (imageBleedElement) {
          var imageVleedContainer = imageBleedElement.parentElement;
          var headerMainContent = document.querySelector('.bdb-header__main-content');
          imageVleedContainer.style.top = "-".concat(headerMainContent.offsetHeight + alertHeight, "px");
          imageVleedContainer.style.marginBottom = "-".concat(headerMainContent.offsetHeight + alertHeight, "px");
        }
    
        if (alertElement) {
          jquery__WEBPACK_IMPORTED_MODULE_1___default()(alertElement).css('visibility', 'visible');
        }
    
        jquery__WEBPACK_IMPORTED_MODULE_1___default()(heroCarouselContainer).css('visibility', 'visible');
      }
      /**
       * Logic to run when component is ready
       * @returns {void}
       */
    
    
      function init() {
        allignCarouselOnBreadcrumbs();
      }
      /**
       * Append listeners to the element.
       * @returns {void}
       */
    
    
      function addListeners() {
        var heroBannerPlayerRef = null;
        var carouselPlayerRef = null; // TODO remove inline comments, just example code.
        // To attach an event, use one of the following methods.
        // No need remove the listener in `instance.detached`
        // instance.addEventListener('click', onClick); //attached to `instance.element`
        // instance.addEventListener('click', onClick, myEl); //attached to `myEl`
    
        jquery__WEBPACK_IMPORTED_MODULE_1___default()(document).on('shown.bs.modal', '#brightcove-video-modal', function (event) {
          Object(_brightcove_player_loader__WEBPACK_IMPORTED_MODULE_0__["default"])({
            refNode: event.target.querySelector('#player-container'),
            accountId: event.relatedTarget.getAttribute('data-accountId'),
            playerId: event.relatedTarget.getAttribute('data-playerId'),
            videoId: event.relatedTarget.getAttribute('data-videoId'),
            options: {
              aspectRatio: '16:9'
            }
          }).then(function (success) {
            carouselPlayerRef = success.ref;
          }).catch(function (error) {
            throw error;
          });
        });
        jquery__WEBPACK_IMPORTED_MODULE_1___default()(document).on('hide.bs.modal', '#brightcove-video-modal', function (event) {
          if (carouselPlayerRef) {
            carouselPlayerRef.pause();
            carouselPlayerRef = null;
          }
    
          jquery__WEBPACK_IMPORTED_MODULE_1___default()(this).find('#player-container').empty();
        });
        jquery__WEBPACK_IMPORTED_MODULE_1___default()(document).on('shown.bs.modal', '#hero-video-modal', function (event) {
          Object(_brightcove_player_loader__WEBPACK_IMPORTED_MODULE_0__["default"])({
            refNode: event.target.querySelector('#player-container'),
            accountId: event.relatedTarget.getAttribute('data-accountId'),
            playerId: event.relatedTarget.getAttribute('data-playerId'),
            videoId: event.relatedTarget.getAttribute('data-videoId'),
            options: {
              aspectRatio: '16:9'
            }
          }).then(function (success) {
            console.log(success);
            heroBannerPlayerRef = success.ref;
          }).catch(function (error) {
            throw error;
          });
        });
        jquery__WEBPACK_IMPORTED_MODULE_1___default()(document).on('hide.bs.modal', '#hero-video-modal', function (event) {
          if (heroBannerPlayerRef) {
            heroBannerPlayerRef.pause();
            heroBannerPlayerRef = null;
          }
    
          console.log(jquery__WEBPACK_IMPORTED_MODULE_1___default()(this).find('#player-container'));
          jquery__WEBPACK_IMPORTED_MODULE_1___default()(this).find('#player-container').empty();
        });
        window.addEventListener('resize', allignCarouselOnBreadcrumbs);
      }
      /**
       * Dealloc variables and removes any added listeners.
       * @returns {void}
       */
    
    
      function dispose() {
        window.removeEventListener('resize', allignCarouselOnBreadcrumbs);
      }
      /**
       * The DOM Element was added to the DOM.
       * @returns {void}
       */
    
    
      instance.attached = function () {
        initDOMReferences();
        init();
        addListeners();
      };
      /**
       * The DOM Element was removed from the DOM.
       * Dealloc variables and removes any added listeners that was NOT added
       * through `instance.addEventListener`.
       * @returns {void}
       */
    
    
      instance.detached = function () {
        // console.log('detached!', instance.element);
        dispose();
      };
    
      return instance;
    }
    
    registerComponent(componentReference, createCarouselInstance);
    
    /***/ }),
    
    /***/ "./src/hbs-app/fe-components/core-carousel/core-carousel.scss":
    /*!********************************************************************!*\
      !*** ./src/hbs-app/fe-components/core-carousel/core-carousel.scss ***!
      \********************************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    // extracted by mini-css-extract-plugin
    module.exports = {"belowdesktoptablet":"only screen and (max-width: 63.938em)","desktopandabove":"only screen and (min-width: 64em)","desktopandbelow":"only screen and (max-width: 56.18em)","desktopmin":"only screen and (min-width: 80em)","desktoptabletabove":"only screen and (min-width: 64em)","ipadabove":"only screen and (min-width: 48em)","ipadbelow":"only screen and (max-width: 47.938em)","mobile":"only screen and (max-width: 37.5em)","tabletandabove":"only screen and (min-width: 50.0625em)","tabletandbelow":"only screen and (max-width: 50em)","tabletmax":"only screen and (max-width: 79.938em)"};
    
    /***/ }),
    
    /***/ "./src/lib/base-component.js":
    /*!***********************************!*\
      !*** ./src/lib/base-component.js ***!
      \***********************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * The base for all components.
     *
     * @module component-register
     * @version 1.0.0
     * @since Sun Sep 20 2015
     */
    
    
    var forEach = __webpack_require__(/*! lodash/forEach */ "./node_modules/lodash/forEach.js");
    
    var isFunction = __webpack_require__(/*! lodash/isFunction */ "./node_modules/lodash/isFunction.js");
    
    var isUndefined = __webpack_require__(/*! lodash/isUndefined */ "./node_modules/lodash/isUndefined.js");
    
    var keys = __webpack_require__(/*! lodash/keys */ "./node_modules/lodash/keys.js");
    
    var domRefs = __webpack_require__(/*! ./dom-refs */ "./src/lib/dom-refs.js");
    
    var getChildInstances = __webpack_require__(/*! ./get-child-instances */ "./src/lib/get-child-instances.js");
    
    var createListenerObject = __webpack_require__(/*! ./listener-object */ "./src/lib/listener-object.js");
    
    var isChildOf = __webpack_require__(/*! ./dom/is-child-of */ "./src/lib/dom/is-child-of/index.js");
    /**
     * Creates a base structure for components.
     *
     * @return {object} The component instance.
     */
    
    
    function createBaseComponent() {
      /**
       * Component instance.
       * @type {Object}
       */
      var instance = {};
      /**
       * A list of added DOM listeners.
       * @type {Object}
       */
    
      var listeners = {};
      /**
       * Since properties are read only, it is not possible to iterate over
       * the keys in `instance.params`. Therefore each key is stored separately.
       * @type {Array}
       */
    
      var paramsKeys = [];
      /**
       * Parameters from parent component.
       * @type {Object}
       */
    
      instance.params = {};
      /**
       * The instance was created. Called before `instance.attached`.
       * Can be overriden to for example update a child component params property.
       */
    
      instance.createdCallback = function () {
        if (isFunction(this.created)) {
          this.created();
        }
      };
      /**
       * The DOM Element was added to the DOM.
       */
    
    
      instance.attachedCallback = function () {
        if (this.domRefs) {
          domRefs(this);
        }
    
        if (isFunction(this.attached)) {
          this.attached();
        }
      };
      /**
       * The DOM Element was removed from the DOM.
       */
    
    
      instance.detachedCallback = function () {
        if (isFunction(this.detached)) {
          this.detached();
        }
    
        dispose.call(this);
      };
      /**
       * Appends a listener to the instance element. An event `type` is only appended
       * once, after that the `listener` will be stacked and called in order.
       *
       * @param  {string} type        Event type, i.e. 'click'.
       * @param  {function} callback  Callback function.
       * @param  {HTMLElement} opt_element Optional DOMElement, must be child of `instance.element`.
       */
    
    
      instance.addEventListener = function (type, callback, opt_element) {
        if (opt_element === null) {
          return;
        }
    
        if (opt_element && !isChildOf(this.element, opt_element)) {
          return;
        }
    
        opt_element = opt_element || this.element;
        var listenerObj = initializeListenerByType(type, opt_element); //appends the callback to an already existing DOM listener.
    
        if (listenerObj === listeners[type][listenerObj.id]) {
          listenerObj.addCallback(callback);
          return;
        }
    
        appendListenerToElement(type, listenerObj, callback);
      };
      /**
       * Tries to find an already exsisting listener object. If not found it will
       * create one and push it to the type array.
       *
       * @param  {string} type
       * @param  {HTMLElement} element
       * @return {object}
       */
    
    
      function initializeListenerByType(type, element) {
        var obj = getListenerByElement(type, element);
    
        if (!obj) {
          obj = createListenerObject(element);
    
          if (listeners[type]) {
            listeners[type].elements.push(obj);
          }
        }
    
        if (!listeners[type]) {
          listeners[type] = {
            elements: [obj]
          };
        }
    
        return obj;
      }
      /**
       * Iterates the list of listener objects for a listener object matching `element`.
       *
       * @param  {string} type
       * @param  {HTMLElement} element
       * @return {undefined | object}
       */
    
    
      function getListenerByElement(type, element) {
        if (!listeners[type]) {
          return;
        }
    
        var index = listeners[type].elements.length;
    
        while (index--) {
          if (listeners[type].elements[index].element === element) {
            return listeners[type].elements[index];
          }
        }
      }
      /**
       * Creates a new listener callback, which will iterate all callbacks
       * added to the same event and element.
       *
       * @param  {string} type
       * @param  {string} id
       * @return {function}
       */
    
    
      function createEventListener(type, id) {
        return function onEventTriggered(event) {
          listeners[type][id].triggerCallbacks(event);
        };
      }
      /**
       * Creates an event listener function and appends it to the DOM element.
       * Also creates a list for the actual callbacks, so when the event is
       * triggered, each callback will be called. This to avoid adding multiple
       * listeners to an element.
       *
       * @param  {string}   type
       * @param  {object}   listenerObj
       * @param  {Function} callback
       */
    
    
      function appendListenerToElement(type, listenerObj, callback) {
        listenerObj.listener = createEventListener(type, listenerObj.id);
        listenerObj.addCallback(callback);
        listeners[type][listenerObj.id] = listenerObj;
        listenerObj.element.addEventListener(type, listenerObj.listener, false);
      }
      /**
       * Removes a listener from the instance element. Will look for already added
       * event `type`.
       *
       * @param  {string} type        Event type, i.e. 'click'.
       * @param  {function} callback  Callback function.
       * @param  {HTMLElement} opt_element Optional DOMElement, must be child of `instance.element`.
       */
    
    
      instance.removeEventListener = function (type, callback, opt_element) {
        if (opt_element === null) {
          return;
        }
    
        if (opt_element && !isChildOf(instance.element, opt_element)) {
          return;
        }
    
        if (!listeners[type]) {
          return;
        }
    
        opt_element = opt_element || this.element;
        var listenerObj = getListenerByElement(type, opt_element);
    
        if (!listenerObj) {
          return;
        }
    
        var size = listenerObj.removeCallback(callback);
    
        if (size > 0) {
          return;
        }
    
        opt_element.removeEventListener(type, listenerObj.listener, false);
      };
      /**
       * Returns a list of component instances found within the DOM hiarachy of
       * instance.element.
       *
       * @param  {string} childReference Reference to the component.
       * @return {Array} List of component instances.
       */
    
    
      instance.getInstancesOf = function (childReference) {
        return getChildInstances(this, childReference);
      };
      /**
       * Returns a representation of the DOM node.
       *
       * @return {string} DOM content as a string.
       */
    
    
      instance.htmlToString = function () {
        return this.element.outerHTML;
      };
      /**
       * Updates the instance `params` property.
       * Copy unchanged properties from previous params to new params.
       * Call params-change listener on the receiving instance.
       *
       * @param  {object} params
       */
    
    
      instance.receiveNewParams = function (params) {
        function iterateParamKeys(key) {
          /*jshint validthis: true */
          if (this.params.hasOwnProperty(key) && isUndefined(params[key])) {
            setParamsProperty(params, this.params[key], key);
          }
        }
    
        appendToParamKeys(keys(params));
        forEach(paramsKeys, iterateParamKeys.bind(this));
        forEach(params, function (value, key) {
          var temp = params[key];
          delete params[key];
          setParamsProperty(params, temp, key);
        });
        this.params = params;
    
        if (isFunction(this.onNewParamsReceived)) {
          this.onNewParamsReceived();
        }
      };
      /**
       * Appends any missing keys in `paramsKeys`.
       *
       * @param  {Array} array
       */
    
    
      function appendToParamKeys(array) {
        forEach(array, function (key) {
          if (paramsKeys.indexOf(key) < 0) {
            paramsKeys.push(key);
          }
        });
      }
      /**
       * Updates a key property of `instance.params`.
       *
       * @param {*} value The new value.
       * @param {string} key The key property.
       */
    
    
      function setParamsProperty(params, value, key) {
        Object.defineProperty(params, key, {
          value: value,
          writable: false
        });
      }
      /**
       * Adds a new attribute or changes the value of an existing attribute on the
       * specified element.
       *
       * Will call instance.attributeChanged if it has been set.
       *
       * @param  {string} name  Name of the attribute.
       * @param  {*} value The new value of the attribute.
       *
       * @callback {instance.attributeChanged}
       * @param {string} attr Name of the attribute.
       * @param {*} oldVal The old value of the attribute.
       * @param {*} newVal The new value of the attribute.
       */
    
    
      instance.setAttribute = function (name, value) {
        var oldVal = this.element.getAttribute(name);
        this.element.setAttribute(name, value);
    
        if (!isFunction(this.attributeChanged)) {
          return;
        }
    
        this.attributeChanged(name, oldVal, value);
      };
      /**
       * Dispose event listeners and properties.
       */
    
    
      function dispose() {
        /*jshint validthis: true */
        forEach(listeners, disposeAllListeners.bind(this));
        listeners = undefined;
        delete this.element;
      }
      /**
       * Removes all listeners added to the event `type`.
       *
       * @param  {object} listener
       * @param  {string} type
       */
    
    
      function disposeAllListeners(listener, type) {
        /*jshint validthis: true */
        function disposeListener(element) {
          element.clearCallbacks();
          this.removeEventListener(type, element.listener, element.element);
        }
    
        forEach(listener.elements, disposeListener.bind(this));
      }
    
      return instance;
    }
    
    module.exports = createBaseComponent;
    
    /***/ }),
    
    /***/ "./src/lib/component-list.js":
    /*!***********************************!*\
      !*** ./src/lib/component-list.js ***!
      \***********************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * Manages the storing and retrieving of the component lists.
     *
     * @module component-register
     * @version 1.0.0
     * @since Tue Jul 21 2015
     */
    
    /**
     * Stores the components.
     * @type {Object}
     */
    
    var list = {};
    /**
     * Creates a list for specific component instances.
     *
     * @param  {string} key Reference to the list.
     * @param  {object | function} definition The instance that will be created
     * and linked to the DOM element.
     */
    
    exports.createList = function (key, definition) {
      list[key] = {
        components: [],
        definition: definition
      };
    };
    /**
     * Destroy a list.
     *
     * @param  {string} key Reference to the list.
     */
    
    
    exports.destroyList = function (key) {
      delete list[key];
    };
    /**
     * Return a list based on reference.
     *
     * @param  {string} key Reference to the list.
     * @return {Array | undefined}
     */
    
    
    exports.getList = function (key) {
      return list[key];
    };
    /**
     * Returns every list available.
     *
     * @return {object}
     */
    
    
    exports.all = function () {
      return list;
    };
    
    /***/ }),
    
    /***/ "./src/lib/component-register.js":
    /*!***************************************!*\
      !*** ./src/lib/component-register.js ***!
      \***************************************/
    /*! exports provided: registerComponent, onDynamicDomReady, restructureHierarchy */
    /***/ (function(module, __webpack_exports__, __webpack_require__) {
    
    "use strict";
    __webpack_require__.r(__webpack_exports__);
    /* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "registerComponent", function() { return registerComponent; });
    /* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "onDynamicDomReady", function() { return onDynamicDomReady; });
    /* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "restructureHierarchy", function() { return restructureHierarchy; });
    /**
     * @module component-register
     * @version 1.0.0
     * @since Thu Jun 16 2015
     */
    
    
    var filter = __webpack_require__(/*! lodash/filter */ "./node_modules/lodash/filter.js");
    
    var forEach = __webpack_require__(/*! lodash/forEach */ "./node_modules/lodash/forEach.js");
    
    var isFunction = __webpack_require__(/*! lodash/isFunction */ "./node_modules/lodash/isFunction.js");
    
    var isString = __webpack_require__(/*! lodash/isString */ "./node_modules/lodash/isString.js");
    
    var isPlainObject = __webpack_require__(/*! lodash/isPlainObject */ "./node_modules/lodash/isPlainObject.js");
    
    var classNameStartingWith = __webpack_require__(/*! lib/utils/class-name-starting-with */ "./src/lib/utils/class-name-starting-with/index.js");
    
    var componentList = __webpack_require__(/*! lib/component-list */ "./src/lib/component-list.js");
    
    var linkElementToComponent = __webpack_require__(/*! lib/link-element-to-component */ "./src/lib/link-element-to-component.js");
    
    var rootInstance = __webpack_require__(/*! lib/root-component */ "./src/lib/root-component.js");
    
    var domUtil = __webpack_require__(/*! lib/dom/dom-util */ "./src/lib/dom/dom-util/index.js"); // exports = module.exports = registerComponent;
    
    
    function noop() {}
    /**
     * A list of instances in the same order as the DOM.
     * @type {Array}
     */
    
    
    var hierachy = [];
    /**
     * Changes once attached has been called.
     * @type {Boolean}
     */
    
    var domReadyCalled = false;
    /**
     * when dynamic html is rendered, components need to updated through onDynamicDomReady method.
     * While it is being executed, isOnDynamicDomReadyFnExecuting flag would be true
     */
    
    var isOnDynamicDomReadyFnExecuting = false;
    /**
     * used to hold the dynamic html's components
     */
    
    var dynamicComponentsHierarchy = [];
    /**
     * The project prefix that all components will be using in their html.
     * The register uses this to find all of them on page load.
     * @type {string}
     */
    
    var baseReference = 'project-prefix__';
    /**
     * Registers a new component. `definition` is used to create an instance
     * that will be linked to an element.
     *
     * @param  {string} className
     * @param  {function | object} definition Factory for creating component
     * instances.
     */
    
    function registerComponent(className, definition, executeDomReady) {
      if (!isString(className)) {
        throw 'Must pass definition of the component';
      }
    
      if (!isPlainObject(definition) && !isFunction(definition)) {
        throw 'Must pass component object or function';
      }
    
      componentList.createList(className, definition);
      /* istanbul ignore else */
    
      if (domReadyCalled) {
        handleComponentDefinition(className);
      }
    
      if (executeDomReady) {
        onDomReady();
      }
    }
    /**
     * Iterates all elements and binds them to a component instance.
     */
    
    function onDomReady() {
      /* istanbul ignore if */
      if (!rootInstance.element) {
        rootInstance.element = document.body;
      }
    
      if (rootInstance.element.getAttribute('data-comp-prefix')) {
        baseReference = rootInstance.element.getAttribute('data-comp-prefix');
      }
    
      var allComponentElements = classNameStartingWith(baseReference);
      forEach(allComponentElements, handleComponentElement);
      rootInstance.createHierarchy(hierachy);
      rootInstance.attachInstances();
      domReadyCalled = true;
    }
    /**
     * Iterates all elements in a given context and binds them to a component instance.
     */
    
    
    function onDynamicDomReady(context, parentInstance) {
      isOnDynamicDomReadyFnExecuting = true;
      dynamicComponentsHierarchy = [];
      /* istanbul ignore if */
    
      if (!context) {
        context = document.body;
      }
    
      var allComponentElements = classNameStartingWith(baseReference, context);
      forEach(allComponentElements, handleComponentElement);
      var parentInstanceIndex;
      forEach(hierachy, function (curComponent, index) {
        if (curComponent.element === parentInstance.element) {
          parentInstanceIndex = index;
          return;
        }
      });
      var index = dynamicComponentsHierarchy.length;
    
      while (index--) {
        hierachy.splice(parentInstanceIndex + 1, 0, dynamicComponentsHierarchy[index]);
      }
    
      restructureHierarchy();
      index = dynamicComponentsHierarchy.length;
    
      while (index--) {
        dynamicComponentsHierarchy[index].attachedCallback();
      }
    
      isOnDynamicDomReadyFnExecuting = false;
    }
    /**
     * Find the component reference from the list of class names, then uses
     * that class name to link the element with the component definition.
     *
     * @param  {HTMLElement} el The element to be linked with a component instance.
     */
    
    function handleComponentElement(el) {
      var key = getFullComponentReference(el.className);
      linkEachElementToComponent(componentList.getList(key))(el);
    }
    /**
     * Looking through the list of class names and returns the one starting with
     * `baseReference`.
     *
     * @param  {string} className The full list of class names.
     * @return {string} Returns one class name.
     */
    
    
    function getFullComponentReference(className) {
      return filter(className.split(' '), matchesBaseReference)[0];
    }
    /**
     * Validates if `className` is a component reference.
     *
     * @param  {string} className
     * @return {boolean}
     */
    
    
    function matchesBaseReference(className) {
      return className.indexOf(baseReference) > -1;
    }
    /**
     * Iterates all elements and binds them to a component instance.
     *
     * @param {string} key Class name definition.
     */
    
    
    function handleComponentDefinition(key) {
      var allElements = findElementsWithClassName(key);
      forEach(allElements, linkEachElementToComponent(componentList.getList(key), true));
    }
    /**
     * Finds all elements matching the class name.
     *
     * @param  {string} className Class name to find.
     * @return {NodeList} List of elements.
     */
    
    
    function findElementsWithClassName(className) {
      return document.body.querySelectorAll('.' + className);
    }
    /**
     * Links each element to a component instance.
     *
     * @param  {object} componentsManager Manager for components.
     * @return {function} Link function.
     */
    
    
    function linkEachElementToComponent(componentsManager, doAttach) {
      if (!componentsManager) {
        return noop;
      }
    
      var definition = componentsManager.definition;
      return function linkElement(el) {
        var component = linkElementToComponent(el, definition);
        /* istanbul ignore else */
    
        if (doAttach) {
          component.attachedCallback();
        }
    
        componentsManager.components.push(component);
    
        if (isOnDynamicDomReadyFnExecuting) {
          dynamicComponentsHierarchy.push(component);
        } else {
          hierachy.push(component);
        }
      };
    }
    
    function restructureHierarchy() {
      var index = hierachy.length;
    
      while (index--) {
        hierachy[index].children = [];
    
        if (!hierachy[index].hasOwnProperty('element')) {
          hierachy.splice(index, 1);
        }
      }
    
      rootInstance.createHierarchy(hierachy, true);
    }
    domUtil.ready(onDomReady);
    
    /***/ }),
    
    /***/ "./src/lib/create-component.js":
    /*!*************************************!*\
      !*** ./src/lib/create-component.js ***!
      \*************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * @module component-register
     * @version 1.0.0
     * @since Tue Jul 28 2015
     */
    
    
    var defaults = __webpack_require__(/*! lodash/defaults */ "./node_modules/lodash/defaults.js");
    
    var isFunction = __webpack_require__(/*! lodash/isFunction */ "./node_modules/lodash/isFunction.js");
    
    var createBaseComponent = __webpack_require__(/*! ./base-component */ "./src/lib/base-component.js");
    /**
     * Generates the instance using `definition`.
     *
     * @param  {object | function} definition Instance definition.
     * @return {object} The newly created instance.
     */
    
    
    function createInstance(definition) {
      if (isFunction(definition)) {
        return definition();
      }
    
      return Object.create(definition);
    }
    /**
     * If `definition` is a function, the factory method is called and return
     * the component instance from the method.
     * Else it creates a new object, using the definitions prototype.
     *
     * Creates the instance and extend a base component instance to it.
     *
     * @param  {object | function} definition Instance definition.
     * @return {object} The newly created instance.
     */
    
    
    function createComponent(definition) {
      var instance = createBaseComponent();
      return defaults(createInstance(definition), instance);
    }
    
    module.exports = createComponent;
    
    /***/ }),
    
    /***/ "./src/lib/dom-refs.js":
    /*!*****************************!*\
      !*** ./src/lib/dom-refs.js ***!
      \*****************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * Finds all elements using the selectors passed in and adds the element
     * to the passed in key for the selector.
     *
     * @module component-register
     * @version 1.0.0
     * @since Sat May 28 2016
     */
    
    
    function _typeof(obj) { "@babel/helpers - typeof"; if (typeof Symbol === "function" && typeof Symbol.iterator === "symbol") { _typeof = function _typeof(obj) { return typeof obj; }; } else { _typeof = function _typeof(obj) { return obj && typeof Symbol === "function" && obj.constructor === Symbol && obj !== Symbol.prototype ? "symbol" : typeof obj; }; } return _typeof(obj); }
    
    var forEach = __webpack_require__(/*! lodash/forEach */ "./node_modules/lodash/forEach.js");
    
    var domUtil = __webpack_require__(/*! ./dom/dom-util */ "./src/lib/dom/dom-util/index.js");
    /**
     * Updates `instance.domRefs`, adds a reference for each selector.
     *
     * @param  {object} instance
     */
    
    
    function domRefs(instance) {
      instance.domRefs = instance.domRefs || {};
      forEach(instance.domRefs.first, appendFirstReference.bind(instance));
      forEach(instance.domRefs.all, appendAllReference.bind(instance));
    }
    /**
     * Binds an element to the `reference`.
     *
     * @param  {string} suffixSelector
     * @param  {string} reference
     */
    
    
    function appendFirstReference(suffixSelector, reference) {
      // when sufficSelector is an object, that means the component's domRefs have already been parsed earlier
      if (_typeof(suffixSelector) === 'object') {
        return;
      }
      /*jshint validthis: true */
    
    
      var instance = this;
      var selector = makeSelector(instance.domRefs, suffixSelector);
      var childEl = find('findFirst', selector, instance.element);
      instance.domRefs.first[reference] = childEl;
    }
    /**
     * Binds an element to the `reference`.
     *
     * @param  {string} suffixSelector
     * @param  {string} reference
     */
    
    
    function appendAllReference(suffixSelector, reference) {
      if (_typeof(suffixSelector) === 'object') {
        return;
      }
      /*jshint validthis: true */
    
    
      var instance = this;
      var selector = makeSelector(instance.domRefs, suffixSelector);
      var childEls = find('findAll', selector, instance.element);
      instance.domRefs.all[reference] = toArray(childEls);
    }
    /**
     * Creates the full selector.
     *
     * @param  {object} domRefs
     * @param  {string} suffixSelector
     * @return {string}
     */
    
    
    function makeSelector(domRefs, suffixSelector) {
      return '.' + domRefs.definition + suffixSelector;
    }
    /**
     * Returns a list of elements or a single element from `element`.
     *
     * @param  {string} method
     * @param  {string} selector
     * @param  {HTMLElement} element
     * @return {NodeList | HTMLElement}
     */
    
    
    function find(method, selector, element) {
      return domUtil[method](selector, element);
    }
    /**
     * Converts `value` to an `Array`.
     *
     * @param  {*} value
     * @return {Array}
     */
    
    
    function toArray(value) {
      return Array.prototype.slice.call(value);
    }
    
    module.exports = domRefs;
    
    /***/ }),
    
    /***/ "./src/lib/dom/dom-util/index.js":
    /*!***************************************!*\
      !*** ./src/lib/dom/dom-util/index.js ***!
      \***************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * DOM utilities to use native browser capabilities and avoid dependencies.
     *
     * @module dom.dom-util
     * @version 1.0.0
     * @since Fri Nov 20 2015
     */
    
    /**
     * Native DOM ready function.
     *
     * @param  {Function} callback
     */
    
    exports.ready = function (callback) {
      document.addEventListener('DOMContentLoaded', callback, false);
    };
    /**
     * Native querySelectorAll to find all the dom element with given selector.
     *
     * @param  {String | Object} selector Target selector element.
     * @param  {String | Object} context  Context/parent element, if it not provided
     * fallback to document.
     * @return {nodeList | Array} Returns the target as a DOM node.
     */
    
    
    exports.findAll = function (selector, context) {
      return (context || document).querySelectorAll(selector);
    };
    /**
     * Find closest element based of element, selector
     *
     * @param {Object} el This is HTML element.
     * @param {String} selector This will be used as selector for closest element
     * element.
     * @param {string} stopSelector optional; if we want to search till which element
     * @return {Boolean} Returns true/false
     */
    
    
    exports.closest = function (el, selector, stopSelector) {
      var retval = null;
    
      while (el) {
        if (exports.hasClass(el, selector)) {
          retval = el;
          break;
        } else if (stopSelector && exports.hasClass(el, stopSelector)) {
          break;
        }
    
        el = el.parentElement;
      }
    
      return retval;
    };
    /**
     * Checks if the queried id existance on the parent nodes.
     *
     * @param {Object} el This is HTML element.
     * @param {String} id This will be used as id selector for parent
     * element.
     * @return {Boolean} Returns true/false
     */
    
    
    exports.hasParent = function (ele, id) {
      if (!ele) return false;
      var el = ele.target || ele.srcElement || ele || false;
    
      while (el && el.id != id) {
        el = el.parentNode || false;
      }
    
      return el !== false;
    };
    /**
     * Native querySelector to find first element in the dom with given selector
     *
     * @param  {String | Object} selector Target selector element.
     * @param  {String | Object} context  Context/parent element, if it not provided
     * fallback to document.
     * @return {Object} returns the target as a DOM node
     */
    
    
    exports.findFirst = function (selector, context) {
      return (context || document).querySelector(selector);
    };
    /**
     * Checks for classList native support.
     *
     * @return {Boolean} Returns true/false.
     */
    
    
    function checkClassListSupport() {
      return 'classList' in document.documentElement ? true : false;
    } // /**
    //  * Find closest element based of element, selector
    //  *
    //  * @param {Object} el This is HTML element.
    //  * @param {String} selector This will be used as selector for closest element
    //  * element.
    //  * @param {string} stopSelector optional; if we want to search till which element
    //  * @return {Boolean} Returns true/false
    //  */
    // exports.closest = function(el, selector, stopSelector) {
    //     var retval = null;
    //     while (el) {
    //         if (this.hasClass(el, selector)) {
    //             retval = el;
    //             break;
    //         } else if (stopSelector && this.hasClass(el, stopSelector)) {
    //             break;
    //         }
    //         el = el.parentElement;
    //     }
    //     return retval;
    // };
    
    /**
     * Checks if the queried id existance on the parent nodes.
     *
     * @param {Object} el This is HTML element.
     * @param {String} id This will be used as id selector for parent
     * element.
     * @return {Boolean} Returns true/false
     */
    
    
    exports.hasParent = function (ele, id) {
      if (!ele) return false;
      var el = ele.target || ele.srcElement || ele || false;
    
      while (el && el.id != id) {
        el = el.parentNode || false;
      }
    
      return el !== false;
    };
    /**
     * Checks if the queried classname existance on the element.
     *
     * @param {Object} el This is HTML element.
     * @param {String} className This will be used a new style class added to the
     * element.
     * @return {Boolean} Returns true/false
     */
    
    
    exports.hasClass = function (el, className) {
      var classListSupport = checkClassListSupport(); // check if the element is not null and classList is supported
    
      if (el && classListSupport) {
        return el.classList.contains(className);
      } // fallback for non-classlist supported browsers
    
    
      if (el && !classListSupport) {
        return new RegExp('\\b' + className + '\\b').test(el.className);
      }
    };
    /**
     * Adds style class to the supplied element.
     *
     * @param {Object} el This is HTML element.
     * @param {String} className This will be used as a style class to be added to
     * the element.
     */
    
    
    exports.addClass = function (el, className) {
      var classListSupport = checkClassListSupport(); // check if the element is not null and classList is supported
    
      if (el && classListSupport) {
        el.classList.add(className);
      } // fallback for non-classlist supported browsers
    
    
      if (el && !classListSupport && !exports.hasClass(el, className)) {
        el.className += ' ' + className;
      }
    };
    /**
     * Removes style class from the supplied element.
     *
     * @param {Object} el This is HTML element.
     * @param {String} className This will be used as a style class to be removed
     * from the element.
     */
    
    
    exports.removeClass = function (el, className) {
      var classListSupport = checkClassListSupport(); // check if the element is not null and classList is supported
    
      if (el && classListSupport) {
        el.classList.remove(className);
      } // fallback for non-classlist supported browsers
    
    
      if (el && !classListSupport) {
        el.className = el.className.replace(new RegExp('\\b' + className + '\\b', 'g'), '');
      }
    };
    /**
     * Based on the browser test stats form below URL, for loop is much faster than
     * any other implementation, if we catch the variables, so this below utility
     * method will help avoiding lodash dependency
     * https://jsperf.com/angular-foreach-vs-native-for-loop/29
     *
     * @param  {Array}   array
     * @param  {Function} callback
     */
    
    
    exports.forEach = function (array, callback) {
      var i;
      var length = array.length;
    
      for (i = 0; i < length; i++) {
        var ele = array[i]; // callback with element as a parameter
    
        callback(ele);
      }
    };
    /**
     * Return the DOM element client height regardless if the elment
     * is vissible or hidden by display CSS property
     *
     * @param  {Object} el
     * @return {Number}
     */
    
    
    exports.getClientHeight = function (el) {
      var el_style = window.getComputedStyle(el),
          el_display = el_style.display,
          el_position = el_style.position,
          el_visibility = el_style.visibility,
          el_max_height = el_style.maxHeight.replace('px', '').replace('%', ''),
          wanted_height = 0; // if its not hidden we just return normal height
    
      if (el_display !== 'none' && el_max_height !== '0') {
        return el.offsetHeight;
      } // the element is hidden so:
      // making the el block so we can meassure its height but still be hidden
    
    
      el.style.position = 'absolute';
      el.style.visibility = 'hidden';
      el.style.display = 'block';
      wanted_height = el.offsetHeight; // reverting to the original values
    
      el.style.display = el_display;
      el.style.position = el_position;
      el.style.visibility = el_visibility;
      return wanted_height;
    };
    
    /***/ }),
    
    /***/ "./src/lib/dom/get-data-attributes/index.js":
    /*!**************************************************!*\
      !*** ./src/lib/dom/get-data-attributes/index.js ***!
      \**************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * Returns all `data-` attributes from an element.
     *
     * @module dom
     * @version 1.0.0
     * @since Say Nov 7 2015
     */
    
    
    var camelCase = __webpack_require__(/*! lodash/camelCase */ "./node_modules/lodash/camelCase.js");
    
    var forEach = __webpack_require__(/*! lodash/forEach */ "./node_modules/lodash/forEach.js");
    /**
     * Iterates the list of attributes from `el` and returns a dictionary of found
     * data- attributes. Each attribute name will be converted to camelCase.
     *
     * @param  {HTMLElement} el The element to work with.
     * @return {object} A dictionary with the attributes name and value.
     */
    
    
    function getDataAttributes(el) {
      if (!el) {
        return '';
      }
    
      var data = {};
      forEach(el.attributes, function (attr) {
        if (/^data-/.test(attr.name)) {
          var camelCaseName = camelCase(attr.name.substr(5));
          data[camelCaseName] = attr.value;
        }
      });
      return data;
    }
    
    module.exports = getDataAttributes;
    
    /***/ }),
    
    /***/ "./src/lib/dom/is-child-of/index.js":
    /*!******************************************!*\
      !*** ./src/lib/dom/is-child-of/index.js ***!
      \******************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * Tries to find an element within a parent element.
     *
     * @module dom.is-child-of
     * @version 1.0.0
     * @since Sat Sep 19 2015
     */
    
    
    var isObject = __webpack_require__(/*! lodash/isObject */ "./node_modules/lodash/isObject.js");
    /**
     * Check if `childNode` is found in `parentNode`.
     *
     * @param  {*}  parentNode The node to look in.
     * @param  {*}  childNode The node to look for.
     * @returns {boolean} Returns `true` if `parentNode` contains `childNode`, else `false`.
     */
    
    
    function isChildOf(parentNode, childNode) {
      if (!isObject(parentNode) || !isObject(childNode)) {
        return false;
      }
    
      if (!isObject(parentNode.contains)) {
        return false;
      }
    
      if (childNode === document.body) {
        return false;
      }
    
      return parentNode.contains(childNode);
    }
    
    module.exports = isChildOf;
    
    /***/ }),
    
    /***/ "./src/lib/get-child-instances.js":
    /*!****************************************!*\
      !*** ./src/lib/get-child-instances.js ***!
      \****************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * Uses a `component` and finds instances matching `childReference`. Looks though
     * the DOM based on the `component`.
     *
     * @module component-register
     * @version 1.0.0
     * @since Sat Sep 19 2015
     */
    
    
    var isArray = __webpack_require__(/*! lodash/isArray */ "./node_modules/lodash/isArray.js");
    
    var isPlainObject = __webpack_require__(/*! lodash/isPlainObject */ "./node_modules/lodash/isPlainObject.js");
    
    var componentList = __webpack_require__(/*! ./component-list */ "./src/lib/component-list.js");
    
    var isChildOf = __webpack_require__(/*! ./dom/is-child-of */ "./src/lib/dom/is-child-of/index.js");
    /**
     * Returns all instances that has an element placed in the same
     * hiarachy as the `component` element.
     *
     * @param  {object} component      The parent component.
     * @param  {string} childReference Reference to the component.
     * @return {Array}                A list of all found instances.
     */
    
    
    function getChildInstances(component, childReference) {
      var list = componentList.getList(childReference);
      var result = [];
    
      if (!isPlainObject(component) || !isPlainObject(list) || !isArray(list.components)) {
        return result;
      }
    
      var componentEl = component.element;
      var index = -1;
      var length = list.components.length;
    
      while (++index < length) {
        if (isChildOf(componentEl, list.components[index].element)) {
          result.push(list.components[index]);
        }
      }
    
      return result;
    }
    
    module.exports = getChildInstances;
    
    /***/ }),
    
    /***/ "./src/lib/link-element-to-component.js":
    /*!**********************************************!*\
      !*** ./src/lib/link-element-to-component.js ***!
      \**********************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * @module component-register
     * @version 1.0.0
     * @since Tue Jul 28 2015
     */
    
    
    var createComponent = __webpack_require__(/*! ./create-component */ "./src/lib/create-component.js");
    
    var getDataAttributes = __webpack_require__(/*! ./dom/get-data-attributes */ "./src/lib/dom/get-data-attributes/index.js");
    /**
     * Creates a component instance and links the element to the instance.
     *
     * @param {HTMLElement} el Element to be linked to a component instance.
     * @param {function} definition Factory method or object to create a new
     * component instance.
     */
    
    
    function linkElementToComponent(el, definition) {
      var component = createComponent(definition);
      component.element = el;
      component.attributes = getDataAttributes(el);
      return component;
    }
    
    module.exports = linkElementToComponent;
    
    /***/ }),
    
    /***/ "./src/lib/listener-object.js":
    /*!************************************!*\
      !*** ./src/lib/listener-object.js ***!
      \************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * An object dealing with elements, callbacks and event listeners.
     *
     * @module component-register
     * @version 1.0.0
     * @since Sun Jan 5 2016
     */
    
    
    var forEach = __webpack_require__(/*! lodash/forEach */ "./node_modules/lodash/forEach.js");
    
    var filter = __webpack_require__(/*! lodash/filter */ "./node_modules/lodash/filter.js");
    
    var isFunction = __webpack_require__(/*! lodash/isFunction */ "./node_modules/lodash/isFunction.js");
    
    var uniqueId = __webpack_require__(/*! lodash/uniqueId */ "./node_modules/lodash/uniqueId.js");
    /**
     * Triggers the passed `callback`.
     *
     * @param  {Function} callback
     */
    
    
    function triggerCallback(callback) {
      /*jshint validthis: true */
      callback(this);
    }
    /**
     * Creates a id object.
     *
     * @param  {HTMLElement} element
     * @return {object}
     */
    
    
    function createListenerObject(element) {
      /**
       * Component instance.
       * @type {Object}
       */
      var instance = {};
      /**
       * A list of callbacks to be iterated and triggered once event is triggered.
       * @type {Array}
       */
    
      var callbacks = [];
      /**
       * A unique id for the instance.
       * @type {string}
       */
    
      instance.id = uniqueId();
      /**
       * The element which to add an event listener to.
       * @type {HTMLElement}
       */
    
      instance.element = element;
      /**
       * Adds the function to the list of callbacks.
       *
       * @param  {Function} callback
       */
    
      instance.addCallback = function (callback) {
        if (!isFunction(callback)) {
          return;
        }
    
        callbacks.push(callback);
      };
      /**
       * Returns the size of the `callbacks` array.
       *
       * @return  {number}
       */
    
    
      instance.numberOfCallbacks = function () {
        return callbacks.length;
      };
      /**
       * Iterates the list of added callbacks and triggeres each one in order.
       *
       * @param {*} args
       */
    
    
      instance.triggerCallbacks = function (args) {
        forEach(callbacks, triggerCallback.bind(args));
      };
      /**
       * Removes any match of `aFunc` from the list of `callbacks`.
       *
       * @param  {Function} aFunc
       * @return {number} The number of callbacks.
       */
    
    
      instance.removeCallback = function (aFunc) {
        callbacks = filter(callbacks, function (callback) {
          return callback !== aFunc;
        });
        return instance.numberOfCallbacks();
      };
      /**
       * Removes everything from the `callbacks` list.
       */
    
    
      instance.clearCallbacks = function () {
        callbacks.length = 0;
      };
    
      return instance;
    }
    
    module.exports = createListenerObject;
    
    /***/ }),
    
    /***/ "./src/lib/root-component.js":
    /*!***********************************!*\
      !*** ./src/lib/root-component.js ***!
      \***********************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    /**
     * The root component. Linked to the body element.
     *
     * @module component-register
     * @version 1.0.0
     * @since Thu Dec 10 2015
     */
     //dependencies
    
    var isArray = __webpack_require__(/*! lodash/isArray */ "./node_modules/lodash/isArray.js");
    
    var isChildOf = __webpack_require__(/*! ./dom/is-child-of */ "./src/lib/dom/is-child-of/index.js");
    /**
     * Element linked to the instance.
     * @type {HTMLElement}
     */
    
    
    exports.element = document.body;
    /**
     * Calls each instance `attached` function. Iterates down to child level and
     * move up.
     */
    
    exports.attachInstances = function () {
      attachChildren(exports.children);
    };
    /**
     * Iterates the list of components and link parents with children.
     * Once the link is created, `instance.created` will be called.
     */
    
    
    exports.createHierarchy = function (orderedList, clearChildren) {
      if (!orderedList || orderedList.length < 1) {
        return;
      }
    
      var index = 0;
      var length = orderedList.length;
      var firstChild = orderedList[0];
    
      if (clearChildren) {
        delete exports.children;
      }
    
      setParent(exports, firstChild);
    
      while (++index < length) {
        var prev = orderedList[index - 1];
        var curr = orderedList[index];
    
        if (isChildOf(prev.element, curr.element)) {
          setParent(prev, curr);
        } else {
          findParent(prev.parent, curr);
        }
      }
    
      callCreated(exports.children);
    };
    /**
     * Calls `attached` for each child.
     *
     * @param  {Array} children List of instances.
     */
    
    
    function attachChildren(children) {
      if (!children) {
        return;
      }
    
      var index = -1;
      var length = children.length;
    
      while (++index < length) {
        var child = children[index];
    
        if (child.children) {
          attachChildren(child.children);
        }
    
        child.attachedCallback();
      }
    }
    /**
     * Calls `created` for each child.
     *
     * @param  {Array} children List of instances.
     */
    
    
    function callCreated(children) {
      /* istanbul ignore if */
      if (!children) {
        return;
      }
    
      var index = -1;
      var length = children.length;
    
      while (++index < length) {
        var child = children[index];
    
        if (child.children) {
          callCreated(child.children);
        }
    
        child.createdCallback();
      }
    }
    /**
     * Sets the child instance parent property to the parent instance. Also
     * appends the child to the list of children in for the parent.
     *
     * @param {object} parent Parent instance.
     * @param {object} child  Child instance.
     */
    
    
    function setParent(parent, child) {
      if (!isArray(parent.children)) {
        parent.children = [];
      }
    
      child.parent = parent;
      parent.children.push(child);
    }
    /**
     * Keeps iterating up one hierachy until the childs parent is found.
     *
     * @param  {object} parent Parent instance.
     * @param  {object} child  Child instance.
     */
    
    
    function findParent(parent, child) {
      /* istanbul ignore if */
      if (!parent) {
        return;
      }
    
      if (isChildOf(parent.element, child.element)) {
        setParent(parent, child);
        return;
      }
    
      findParent(parent.parent, child);
    }
    
    /***/ }),
    
    /***/ "./src/lib/utils/class-name-starting-with/index.js":
    /*!*********************************************************!*\
      !*** ./src/lib/utils/class-name-starting-with/index.js ***!
      \*********************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    "use strict";
    
    /**
     * Finds all element starting with `string`. Queries the DOM for '[class*="string"]'.
     *
     * @param  {string}                  string        The query string.
     * @param  {undefined | HTMLElement} [rootElement] Optional element to search through.
     * @return {Array}                                 List of found elements.
     */
    
    function classNameStartingWith(string, rootElement) {
      rootElement = rootElement || document.body;
      return Array.prototype.slice.call(rootElement.querySelectorAll('[class*="' + string + '"]'), 0);
    }
    
    module.exports = classNameStartingWith;
    
    /***/ }),
    
    /***/ 2:
    /*!******************************!*\
      !*** min-document (ignored) ***!
      \******************************/
    /*! no static exports found */
    /***/ (function(module, exports) {
    
    /* (ignored) */
    
    /***/ }),
    
    /***/ 3:
    /*!*************************************************************************************************************************************!*\
      !*** multi ./src/hbs-app/fe-components/core-carousel/core-carousel.js ./src/hbs-app/fe-components/core-carousel/core-carousel.scss ***!
      \*************************************************************************************************************************************/
    /*! no static exports found */
    /***/ (function(module, exports, __webpack_require__) {
    
    __webpack_require__(/*! ./src/hbs-app/fe-components/core-carousel/core-carousel.js */"./src/hbs-app/fe-components/core-carousel/core-carousel.js");
    module.exports = __webpack_require__(/*! ./src/hbs-app/fe-components/core-carousel/core-carousel.scss */"./src/hbs-app/fe-components/core-carousel/core-carousel.scss");
    
    
    /***/ })
    
    /******/ });