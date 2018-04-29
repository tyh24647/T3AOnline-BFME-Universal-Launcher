var ajax_interval = 10000;
var ajax_timeout = 10000;
var json_url =  "https://info.server.cnc-online.net/";
var profile_url = "/profiles/"
var image_url = static_url+"images/site/serverinfo/"

__e = escapeHTML;

var gamenames = ['bfme', 'bfme2', 'rotwk'];
var gametitles = {
    'bfme'      : 'Battle for Middle-earth',
    'bfme2'     : 'Battle for Middle-earth II',
    'rotwk'     : 'Rise of the Witch-king'
}



function handleJSON(response, textStatus, jqXHR) {
    var $infobar = $('#infobar');
    var $serverinfos = $('.serverinfo div')
    // clear the containers of old info before we add new one
    clearInfo($infobar, $serverinfos);

    // var totalUsers = 0;
    // for (var user in response.users) {
    //     if (response.users.hasOwnProperty(user)) {
    //         totalUsers++;
    //     }
    // }

    // Userbar info
    setUserbarInfo($infobar, response);

    // Users
    setUserInfo(response);

    // Games
    setGamesInfo(response);
}


function handleJSONError(XMLHttpRequest, textStatus, errorThrown) {
    var $infobar = $('#infobar');
    var $serverinfo = $('.serverinfo div');
    // clear the containers of old info before we add new one
    clearInfo($serverinfo, $infobar);

    $infobar.append('<p>Server Status: <span class="offline">OFFLINE</span></p>');
}


function clearInfo($infobar, $serverinfos) {
    $serverinfos.each(function(i, obj) {
        $(this).html('');
    })
    $infobar.html('');
}


function setUserbarInfo($parent, response) {
    var userBarHTML = '';
    var totalUsers = 0;

    for (var i = 0; i < gamenames.length; i++) {
        var gamename = gamenames[i];
        var gametitle = gametitles[gamename];
        var countUsers = 0;
        var users = response[gamename].users;
        for (var user in users) {
            if (users.hasOwnProperty(user)) {
                countUsers++;
            }
        }
        userBarHTML += '<a class="fancybox" href="#serverinfo-'+gamename+'"><span class="number">'+countUsers+'</span> '+gametitle+'</a> ';
        totalUsers += countUsers;
    }
    userBarHTML = '<h2><span class="number">'+totalUsers+'</span> Players Online</h2><p> ' + userBarHTML + '</p>';
    $(userBarHTML).appendTo($parent);
}


function setUserInfo(response) {
    for (var i = 0; i < gamenames.length; i++) {
        var gamename = gamenames[i];
        var resp = response[gamename];
        $serverinfo = $('#serverinfo-'+gamename+' div');
        $serverinfo.append(getUserSection(resp, gamename));
    }
}


function getUserSection(response, gamename) {
    var countUsers = 0;
    var users = response.users;

    var $userList = $('<p id="users-list"></p>');
    for (var i in users) {
        user = users[i];
        html = '<a href="'+profile_url+user.pid+'/" title="View Profile">'+__e(user.nickname)+'</a>'
        if (countUsers > 0) {
            $userList.append(', ');
        }
        $userList.append($(html));
        countUsers++;
    }

    var $sectionUsers = $('<section><h3><span class="number">'+countUsers+'</span> Players Online</h3></section>');
    $sectionUsers.append($userList);
    return $sectionUsers
}


function setGamesInfo(response) {
    for (var i = 0; i < gamenames.length; i++)  {
        var gamename = gamenames[i];
        var resp = response[gamename];
        $serverinfo = $('#serverinfo-'+gamename+' div');
        $serverinfo.append(getGamesSection(resp, gamename, false));
        if (gamename != 'generals' && gamename != 'generalszh') {
            // No games in progress list for Generals and ZH
            $serverinfo.append(getGamesSection(resp, gamename, true));
        }
    }
}


function getGamesSection(response, gamename, playing) {
    var games = null;

    if (playing == true) {
        games = response.games.playing;
        state = "playing";
        title = "Games in Progress";
    }
    else {
        games = response.games.staging;
        state = "staging";
        title = "Games in Lobby";
    }

    $gameSection = $('<div id="games-'+state+'-list"><h3><span class="number">'+games.length+'</span> '+title+'</h3></div>');
    $gamesList = $('<table class="games-list table"></table>');
    $gamesList.appendTo($gameSection);

    if (games.length > 0) {
        $gamesList.append('<tr><th>Game</th><th>Map</th><th>Players</th><th>Version</th><th title="Observers">Obs</th></tr>');
    }

    // loop over games
    $.each(games, function(i, game) {
        var pw = pwHTML(game.pw);
        var playerCount = playerCountHTML(game.numRealPlayers, game.numObservers, game.maxRealPlayers);
        $gameItem = $('<tr class="game '+state+'"><td class="game-title">'+pw + __e(game.title)+' '+playerCount+'</td></tr>');
        //$gameItem.appendTo($parent);

        if ( parseInt(game.numRealPlayers)+parseInt(game.numObservers) == parseInt(game.maxRealPlayers) ) {
            $gameItem.addClass('full');
        }
        $gameItem.append('<td class="game-map">'+__e(game.map)+'</td>');

        $gameItem.append('<td class="game-players"><ul class="players-list"></ul></td>');
        if (game.players == 'error') {
            // we don't loop here because we don't actually have player info
            liHTML = '<li class="host"><a href="'+profile_url+game.host.pid+'/">'+__e(game.host.nickname)+'</a></li><li>...</li>';
            $gameItem.find('.players-list').append(liHTML);
        }
        else {
            $.each(game.players, function(j, player) {
                var host = (game.host == '') ? '' : game.host.nickname;
                var playerClass = (player.nickname == host) ? 'host' : 'player';
                liHTML = '<li class="'+playerClass+'"><a href="'+profile_url+player.pid+'/" >'+__e(player.nickname)+'</a></li>';
                $gameItem.find('.players-list').append(liHTML);
            });
        }

        $gameItem.append('<td class="game-version">'+__e(game.version)+'</td>');
        $gameItem.append('<td class="game-observers">'+__e(game.numObservers)+'</td>');

        $gameItem.appendTo($gamesList);
    });

    return $gameSection;
}


function playerCountHTML(numRealPlayers, numObservers, maxRealPlayers) {
    return '('+ (parseInt(numRealPlayers)+parseInt(numObservers)) +'/'+ parseInt(maxRealPlayers) +')';
}

function pwHTML(gamepw) {
    if (parseInt(gamepw) == 1) {
        return '<img src="'+image_url+'lock_bfme_small.png" title="This game requires a password to join" alt="locked" /> ';
    }
    else {
        return '';
    }
}

function pingHTML(pings) {
    var color = '';
    if (pings > 350) {
        color = 'red';
    }
    else if (pings > 100) {
        color = 'yellow';
    }
    else {
        color = 'green';
    }
    return '<img src="'+image_url+'ping_'+color+'.png" title="'+__e(pings)+'ms" alt="'+color+' ping" />';
}


function escapeHTML(string) {
    // taken from _.escape in Underscore

    // List of HTML entities for escaping.
    var htmlEscapes = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#x27;',
        '/': '&#x2F;'
    };

    // Regex containing the keys listed immediately above.
    var htmlEscaper = /[&<>"'\/]/g;

    // Escape a string for HTML interpolation.
    return ('' + string).replace(htmlEscaper, function(match) {
        return htmlEscapes[match];
    });
}



function serverinfo_init() {
    for (var i = 0; i < gamenames.length; i++) {
        var gamename = gamenames[i];
        var gametitle = gametitles[gamename];
        var $serverinfo = $('<div id="serverinfo-'+gamename+'" class="serverinfo"></div>');
        $serverinfo.append('<h2>'+gametitle+' Status</h2>');
        $serverinfo.append('<div/>');
        $('header#top').prepend($serverinfo);
    }

    //
    // JSON
    //
    getJSONInfo();
    setInterval(getJSONInfo, ajax_interval);
    function getJSONInfo() {
        $.ajax({
            url: json_url + "?callback=?",
            dataType: 'jsonp',
            data: null,
            timeout: ajax_timeout,
            success: handleJSON,
            error: handleJSONError,
        });
    }
}

