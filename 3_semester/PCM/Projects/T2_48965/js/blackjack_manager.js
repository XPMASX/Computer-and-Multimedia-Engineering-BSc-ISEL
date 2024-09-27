// pcm 20172018a Blackjack oop

let game = null;

var hidden;

function debug(an_object) {
    document.getElementById("debug").innerHTML = JSON.stringify(an_object);
}

function buttons_initialization(){
    document.getElementById("card").disabled     = false;
    document.getElementById("stand").disabled     = false;
    document.getElementById("new_game").disabled = true;
}

function finalize_buttons(){
    document.getElementById("card").disabled     = true;
    document.getElementById("stand").disabled     = true;
    document.getElementById("new_game").disabled = false;
}

    
//FUNÇÕES QUE DEVEM SER IMPLEMENTADOS PELOS ALUNOS
function new_game(){

    document.getElementById("your-cards").innerHTML = '';
    document.getElementById("hidden").src = "img/BACK.png";
    document.getElementById("dealer-cards").innerHTML = '';
    
    
    let cards = null;
    game = new BlackJack();

    
    dealer_new_card();
    dealer_new_card();
    cards= game.get_dealer_cards();
    hidden = cards[0];
    console.log(hidden);

    

    player_new_card();
    player_new_card();
    buttons_initialization();

    if(game.get_cards_value(game.get_player_cards())==21)
    {
        document.getElementById("message").innerHTML = "BLACKJACK!!! You win!";
        finalize_buttons();
    }
    
    debug(game);
}

function update_dealer(state){

    if(state.gameEnded){
        
        let cards = game.get_dealer_cards();
        let msg = JSON.stringify(cards);

        if(state.dealerWon){
            msg = msg + " LOST";
            console.log("dealer " + msg);
        }
        if(state.dealerBusted){
            msg = msg + " WON";
            console.log("dealer " + msg);
        }
        
    }

}

function update_player(state){

    if(state.gameEnded){
        finalize_buttons();
        let cards = game.get_player_cards();
        let msg = JSON.stringify(cards);

        if(state.playerWon){
            msg = msg + " WON";
            console.log("player " + msg);
        }
        if(state.playerBusted){
            msg = msg + " LOST";
            console.log("player " + msg);
        }
    }
    cards = game.get_player_cards();
    if (state.gameEnded == false ) {
        playerTotal= game.get_cards_value(game.get_player_cards());
        document.getElementById("message").innerHTML = "Tens " + playerTotal + ". Hit or Stand?";
    }
    
    

}

function dealer_new_card(){
    let state = game.dealer_move();
    update_dealer(state);
    debug(game);
    return state;
}

function player_new_card(){
    let state= game.player_move();
    update_player(state);
    
    debug(game);
    return state;
}

function dealer_finish(){
    let state = game.get_game_state();
    

    game.setDealerTurn(true);
    while(!state.gameEnded){

        dealer=game.get_cards_value(game.get_dealer_cards());
        update_dealer(state);
        if (dealer <17) {
            console.log("bro");
            dealer_new_card();
        }   
        dealer=game.get_cards_value(game.get_dealer_cards());
        if (dealer >=17 && dealer < game.get_cards_value(game.get_player_cards())) {
            dealer_new_card();
        }
        

        state = game.get_game_state();
        
        debug(game);
    }
    finalize_buttons();
    
}

