// pcm 20172018a Blackjack object

//constante com o número máximo de pontos para blackJack
const MAX_POINTS = 21;


// Classe BlackJack - construtor
class BlackJack {
    constructor() {
        // array com as cartas do dealer
        this.dealer_cards = [];
        // array com as cartas do player
        this.player_cards = [];
        // variável booleana que indica a vez do dealer jogar até ao fim
        this.dealerTurn = false;

        // objeto na forma literal com o estado do jogo
        this.state = {
            'gameEnded': false,
            'dealerWon': false,
            'playerBusted': false
        };

        //métodos utilizados no construtor (DEVEM SER IMPLEMENTADOS PELOS ALUNOS)
        this.new_deck = function () {
            
                const suits = 4;
                const CARDS_PER_SUIT = 13;
                let deck = [];

                for (let i = 0; i< suits*CARDS_PER_SUIT; i++)
                {
                    deck[i] = (i % CARDS_PER_SUIT) + 1;
                }

                return deck;
        };

        this.shuffle = function (deck) {
            let indexes = [];
            let shuffled = [];
            let index = null;

            for (let n = 0; n < deck.length; n++) {
                indexes.push(n);
            }
            for (let n = 0; n < deck.length; n++) {
                index = Math.floor(Math.random()*indexes.length);
                shuffled.push(deck[indexes[index]]);
                indexes.splice(index,1);
            }

            return shuffled;
        };

        // baralho de cartas baralhado
        this.deck = this.shuffle(this.new_deck());
        
        
        console.log(this.deck.length);
        //this.deck = this.new_deck();
    }

    // métodos
    // devolve as cartas do dealer num novo array (splice)
    get_dealer_cards() {
        return this.dealer_cards.slice();
    }

    // devolve as cartas do player num novo array (splice)
    get_player_cards() {
        return this.player_cards.slice();
    }

    // Ativa a variável booleana "dealerTurn"
    setDealerTurn (val) {
        this.dealerTurn = val;
    }

    //MÉTODOS QUE DEVEM SER IMPLEMENTADOS PELOS ALUNOS
    get_cards_value(cards) {
        let noAces = cards.filter(function(card){return card !=1;});
        let figTransf = noAces.map(function(c){return c > 10? 10:c;});
        let sum = figTransf.reduce(function(sum,value){return sum+=value;},0);
        let numAces = cards.length - noAces.length;
        while (numAces>0) {

            if (sum+11 > MAX_POINTS && numAces>=1) {
                
                return sum + numAces;
            }
            
            
            sum+=11;
            numAces-=1;
        }
        return sum+numAces;
    }

    dealer_move() {

        let cardImg =  document.createElement("img");
        
        let card = this.deck[0];
        cardImg.src = "./img/" + card + this.get_suit() + ".png";
        this.deck.splice(0,1);
        this.dealer_cards.push(card);
        if (this.dealer_cards.length >1) {
            document.getElementById("dealer-cards").appendChild(cardImg);
        }
        
        return this.get_game_state();
    }

    player_move() {
        let cardImg =  document.createElement("img");
        
        let card = this.deck[0];
        cardImg.src = "./img/" + card + this.get_suit() + ".png";
        this.deck.splice(0,1);
        this.player_cards.push(card);
        document.getElementById("your-cards").appendChild(cardImg);
        return this.get_game_state();
    }

    
    get_suit()
    {
        var ran = Math.floor(Math.random() * (3 - 0 + 1) + 0);
        let naipes = ["-C","-S","-D","-H"];

        return naipes[ran];
    }

    get_game_state() {
        let playerPoints = this.get_cards_value(this.player_cards);
        let dealerPoints = this.get_cards_value(this.dealer_cards);
        let playerBusted = playerPoints > MAX_POINTS;
        let playerWon = playerPoints === MAX_POINTS;
        let dealerBusted = this.dealerTurn && (dealerPoints> MAX_POINTS);
        let dealerWon = this.dealerTurn && dealerPoints >= playerPoints && dealerPoints <= MAX_POINTS;
        this.state.gameEnded = playerBusted || playerWon || dealerBusted || dealerWon;
        this.state.dealerWon = dealerWon;
        this.state.playerBusted = playerBusted;
        this.state.playerWon= playerWon;

        var  win = true;
        if (this.state.dealerWon == true || this.state.playerBusted == true) {
            win = false;
        }
        if(this.state.gameEnded){
            document.getElementById("message").innerHTML = (win ? "Congratulations! You win.  " : "Sorry! You lose.  ");    
            document.getElementById("hidden").src = "./img/" + hidden + this.get_suit() + ".png";
        }
        console.log("player p= " + playerPoints);
        console.log("dealer p= " + dealerPoints);
        

        return this.state;
    }
}

