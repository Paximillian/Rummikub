/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


        function updateEvent(event){
                switch(event['type']){
                case 'GAME_START':
                    gameStart();
                    break;
                case 'PLAYER_TURN':
                    setPlayerTurn(event);
                    break;
                case 'TILE_MOVED':
                    moveCard(event.getPlayerName(), event.getSourceSequenceIndex(), event.getSourceSequencePosition(), event.getTargetSequenceIndex(), event.getTargetSequencePosition());
                    break;
                case 'PLAYER_FINISHED_TURN':
                     moveThisTurnList = [];
                    break;
                case 'PLAYER_RESIGNED':
                    //playerResigned(event);
                    break;
            }
        }
        
        function moveCard(playerName, sourceSeqId, sourceSeqPos, targetSeqId, targetSeqPos){
            //$('#sourceSeqId')
            
        }
        
        function gameStart(){
                       
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (xhttp.readyState == 4 && xhttp.status == 200)
                    {                      
                        var playersDetals = jQuery.parseJSON( xhttp.responseText);
                        
                        for(i = 0; i < playersDetals.length; i++)
                        {
                            if(playersDetals[i]['name'] === playerName)
                            {
                                setPlayerDetails(playersDetals[i]);
                                break;
                            }
                        }                      
                    }
                }
                xhttp.open("GET", "Lobby/PlayersDetails", true);
                xhttp.send();                                    
        }
        
        function setPlayerDetails(playerDetails){
            
            playedFirstSequence =playerDetails['playedFirstSequence'];
            tiles = playerDetails['tiles'];
            
            showMyHand();            
        }
        
        function showMyHand()
        {
             $('#hand').html('');
            for(i = 0;i < tiles.length; i++)
            {  
                $('#hand').html($('#hand').html() + "<button ondragstart='getSequenceIndex($(this)); drag(event);' draggable='true' id='tile"+tiles[i]['color']+tiles[i]['value']+"' class='tile "+tiles[i]['color']+"'><h3>"+tiles[i]['value']+"</h3></button>");   
            }           
            lastGameState = $('body').html();
        }
        
function getSequenceIndex(tile){
    var target = tile.parent();
    if(target.attr('id') === 'hand')
    { tileOrg = 0;}
    else
    { tileOrg = target.attr('id') ;}
    
    getTileIndex(tile , target)
}

function getTileIndex(tile, papa){
    tiles = papa.children();
    for(i = 0; i< tiles.length; i++){
        if(tiles[i]['id'] === tile.attr('id'))
            tileOrgInd = i;
    }
    }
    
    function setDropInfo(dimaLoHaver){
        tileDest = dimaLoHaver.attr('id');
        tileDestInd = dimaLoHaver.children().length*1 -1;
        saveMove();
    }
        
function allowDrop(ev) {
    ev.preventDefault();
    
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    var target = ev.target;
    while(target !== undefined && target !== null && target.className !== "sequnceDiv"){
        target = target.parentNode;
    }
    if(target !== undefined && target !== null){
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        target.appendChild(document.getElementById(data));
    }
}
        
    function setPlayerTurn(event){
        $('#currantPlayerName').html("player : "+event['playerName']);
        if(playerName == event['playerName'])
        {
            $('over').remove();
            $('button').removeAttr('disabled');
            moveThisTurnList = [];          
            //playedFirstSequence =playerDetails['playedFirstSequence'];
            tiles = event['tiles'];
            
            showMyHand();
            lastGameState = $('body').html();
        }
    } 
    
    function ResetTurn(){
        
        $('body').html(lastGameState);
        moveThisTurnList =[];       
    }

    
    function saveMove(){
        var lestMove= { sourceSequenceIndex : tileOrg, sourceSequencePosition : tileOrgInd, targetSequenceIndex : tileDest*1, targetSequencePosition: tileDestInd};       
        moveThisTurnList.push(lestMove);
    }
    
    function endTurn(i){
        
        if(i < moveThisTurnList.length)
        {   
            setCookie('sourceSequenceIndex' , moveThisTurnList[i]['sourceSequenceIndex']);
            setCookie('sourceSequencePosition' ,moveThisTurnList[i]['sourceSequencePosition']*1);
            setCookie('targetSequenceIndex' ,moveThisTurnList[i]['targetSequenceIndex']);
            setCookie('targetSequencePosition' ,moveThisTurnList[i]['targetSequencePosition']);
              
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (xhttp.readyState == 4 && xhttp.status == 200)
                {              
                       endTurn(i*1 + 1) ;
                }
            }
          xhttp.open("POST", "Game/MoveTile", true);
          xhttp.send();
      }
      else{
          var xhttp = new XMLHttpRequest();
          xhttp.onreadystatechange = function() {
                if (xhttp.readyState == 4 && xhttp.status == 200)
                {     
                  
                }
          }
          xhttp.open("POST", "Game/FinishTurn", true);
          xhttp.send();
          
          moveThisTurnList =[]; 
          $('button').attr('disabled','disabled');
                    
      }
    }   
    
