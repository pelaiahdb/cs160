/* WE NEED TO GET THE DYNAMODB TO WORK WITH THIS CODE <-- FINISHED... need to check and print
/* Once we do this, we can simply use the following syntax:
/* recipeItem = dynamoDatabase["name of food"]
/* food = recipeItem.name
/* ingredients = recipeItem.ingredients
/* directions = recipeItem.directions
/* image = recipeItem.image */
 
 
 
 
'use strict';
 
var CARD_TITLE = "Recipe Assistant";
 
 
 
// Start of file, global scope.
 
//const rp = require('request-promise');
 
 // FOR NOW WE REPLACE THIS WITH A PREMADE LIST
 
//const recipes_dict = []; // <-this is the most important thing

var great = new Object();
great.RecipeName = "burger";
great.Ingredients = ["happy", "yeah", "suweet"];
great.Directions = ["baby", "what", "donkey"];


 
// Route the incoming request based on type (LaunchRequest, IntentRequest,
// etc.) The JSON body of the request is provided in the event parameter.
exports.handler = function (event, context) {
    try {
        console.log("event.session.application.applicationId=" + event.session.application.applicationId);
 
        /**
         * Uncomment this if statement and populate with your skill's application ID to
         * prevent someone else from configuring a skill that sends requests to this function.
         */
 
//     if (event.session.application.applicationId !== "amzn1.echo-sdk-ams.app.05aecccb3-1461-48fb-a008-822ddrt6b516") {
//         context.fail("Invalid Application ID");
//      }
 
        if (event.session.new) {
            onSessionStarted({requestId: event.request.requestId}, event.session);
        }
 
        if (event.request.type === "LaunchRequest") {
            onLaunch(event.request,
                event.session,
                function callback(sessionAttributes, speechletResponse) {
                    context.succeed(buildResponse(sessionAttributes, speechletResponse));
                });
        } else if (event.request.type === "IntentRequest") {
            onIntent(event.request,
                event.session,
                function callback(sessionAttributes, speechletResponse) {
                    context.succeed(buildResponse(sessionAttributes, speechletResponse));
                });
        } else if (event.request.type === "SessionEndedRequest") {
            onSessionEnded(event.request, event.session);
            context.succeed();
        }
    } catch (e) {
        context.fail("Exception: " + e);
    }
};
 
/**
 * Called when the session starts.
 */
function onSessionStarted(sessionStartedRequest, session) {
    console.log("onSessionStarted requestId=" + sessionStartedRequest.requestId
        + ", sessionId=" + session.sessionId);
 
    // add any session init logic here
}
 
/**
 * Called when the user invokes the skill without specifying what they want.
 */
function onLaunch(launchRequest, session, callback) {
    console.log("onLaunch requestId=" + launchRequest.requestId
        + ", sessionId=" + session.sessionId);
 
 
   getWelcomeResponse(callback);
}

/**
 * Called when the user specifies an intent for this skill.
 */
function onIntent(intentRequest, session, callback) {
    console.log("onIntent requestId=" + intentRequest.requestId
        + ", sessionId=" + session.sessionId);
 
    var intent = intentRequest.intent,
        intentName = intentRequest.intent.name;
 



    if (session.attributes &&(session.attributes.currentDialog === "main")) { // if main dialog

        if ("HelpIntent" === intentName) { // if user needs what can i say
            handleMain(intent, session, callback);
        } else if ("RecipeRetrievalIntent" === intentName) { // user asks for specific food, get that food object from database and set it as currentfood in sessionattribute, set the 
            handleMain(intent, session, callback);
        } else if ("ExitIntent" === intentName) {
            handleFinishSessionRequest(intent, session, callback);
        }
    } else if (session.attributes &&(session.attributes.currentDialog === "ingredients")) { // here, the user has chosen the food, so the session.attribute.currentfood = {chosen food}

        if ("IngredientListingIntent" === intentName) { // user says: "what are the ingredients?"
            handleIngredients(intent, session, callback); // list ingredients ONE BY ONE. USER MUST SAY NEXT. we need new session attribute for the current ingredients index
        } else if ("NextIngredientIntent" === intentName) { // say next ingredient. IF NO MORE INGREDIENTS, LEAD TO DIRECTIONS DIALOG
            handleIngredients(intent, session, callback);
        }else if ("LastIngredientIntent" === intentName) { // say previous ingredient. maybe this repeats first ingredient if no more previous
            handleIngredients(intent, session, callback);
        }else if ("RestartIntent" === intentName) { // go back to first ingredient
            handleIngredients(intent, session, callback);
        } else if ("ExitIntent" === intentName || "MainDialogIntent" === intentName) { // if "main dialog, exit or quit"
            handleMain(intent, session, callback); 
        }
    } else if (session.attributes &&(session.attributes.currentDialog === "directions")) { // THIS IS NOT ACTIVATED AUTOMATICALLY BY THE USER. this only activates after finishing saying all ingredients


    if ("ReadRecipeIntent" === intentName) {

        firstStep(session, callback);

    } else if ("NextStepIntent" === intentName) {

        nextStep(session, callback);

    } else if ("LastStepIntent" === intentName) {

        lastStep(session, callback);

    } else if ("RestartIntent" === intentName) {

        firstStep(session, callback);

    } else if ("ExitIntent" === intentName || "MainDialogIntent" === intentName) { // if "main dialog, exit or quit"

            handleMain(intent, session, callback);
        }
    }
    
    // dispatch custom intents to handlers here <- THIS GETS ACTIVATED IF 

    throw "Invalid intent";
}
 
/**
 * Called when the user ends the session.
 * Is not called when the skill returns shouldEndSession=true.
 */
function onSessionEnded(sessionEndedRequest, session) {
    console.log("onSessionEnded requestId=" + sessionEndedRequest.requestId
        + ", sessionId=" + session.sessionId);
 
    // Add any cleanup logic here
}
 
// ------- Skill specific business logic -------
 
 
 

function getWelcomeResponse(callback) {
    var sessionAttributes = {};
    var speechOutput = "Recipe assistant, what recipe would you like to make?";
    var shouldEndSession = false;
    var repromptText = "What recipe would you like to make?";
   sessionAttributes = {
        "speechOutput": speechOutput,
        "repromptText": repromptText,
        "waitingForOption": true,
        "currentFood": "", // chosen food
        "currentDialog":"main", // we set currentDialog to main
        "ingredients":[],
        "directions":[],
        "directionNumber": 0, // for next, start again, last
        "ingredientNumber": 0 // for next, start again, last
    };
    callback(sessionAttributes,
        buildSpeechletResponse(CARD_TITLE, speechOutput, repromptText, shouldEndSession));
}
 
 
/*I AM CREATING ONE HANDLER FOR EACH OF THE 3 STATES */




// THIS IS MAIN DIALOG DON'T GET CONFUSED
function handleMain (intent, session, callback) {
    var speechOutput = "";

    if (!session.attributes) { // check if no session attributes
        session.attributes = {};
    }
    
    if (intent.name === "HelpIntent" || "MainDialogIntent" === intent.name || "ExitIntent" === intent.name) {
        session.attributes.currentDialog = "main";
        speechOutput = "To search for specific food, you can say the following: find food, i'd like to make food. To end the app, say exit or quit.";
        
        var repromptText = "Repeat above text";
        var shouldEndSession = false;
        callback(session.attributes,
            buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
    } else if (intent.name === "RecipeRetrievalIntent") {
        

        // change dialog to ingredients dialog
        session.attributes.currentDialog = "ingredients";
        
        // change attributes to current food
        session.attributes.currentFood = great.RecipeName;
        session.attributes.directions = great.Directions; // TODO: this needs an extra step for changing one string to a list.... is there an easy way? ingredients are separated by /n
        session.attributes.ingredients = great.Ingredients; // TODO: this needs an extra step for changing one string to a list.... is there an easy way?

        
        
        
        var speechOutput = "You selected " +great.RecipeName+ ". To hear ingredients, say ingredients or what are the ingredients.";
        var repromptText = "To hear ingredients, say ingredients or what are the ingredients.";
        var shouldEndSession = false;
        callback(session.attributes,
            buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
    }
    
    
}



// THIS IS INGREDIENTS DIALOG LOLOL
function handleIngredients (intent, session, callback) {
    var speechOutput = "";
    if (!session.attributes) { // check if no session attributes
        session.attributes = {};
    }
    
    if (intent.name === "IngredientListingIntent") {
        // this is the start of ingredients
        speechOutput = "You are now ready to hear ingredients. You can say next ingredient, last ingredient, or start again.";
        var repromptText = "You can say next ingredient, last ingredient, or start again";
        var shouldEndSession = false;
        callback(session.attributes,
            buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
    } else if (intent.name === "NextIngredientIntent") {
        // GET NEXT INGREDIENT
        if (session.attributes.ingredientNumber < session.attributes.ingredients.length) {  // TODO: don't know if this is how you get array length in js
            speechOutput = session.attributes.ingredients[session.attributes.ingredientNumber]; // get ingredient in index
            var repromptText = speechOutput;
            var shouldEndSession = false;
            // increment index
            session.attributes.ingredientNumber += 1;
            if (session.attributes.ingredientNumber == session.attributes.ingredients.length) { // DON'T TRANSITION TO RECIPE YET. Just say it's last ingredient, say next ingredient to move to Recipe. Or say last ingredient.
                speechOutput += ". That was the last ingredient. Say next ingredient to move to Recipe Steps, or say last ingredient, or start again.";
                var repromptText = "You can say next ingredient, last ingredient, or start again";
            }
            callback(session.attributes,
                buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
        } else {
            // TRANSITION TO RECIPE STEPS/directions
            session.attributes.currentDialog = "directions";
            speechOutput = "To start hearing recipe directions, say read recipe or start.";
            var repromptText = "To hear recipe directions, say read recipe or start.";
            var shouldEndSession = false;
            callback(session.attributes,
                buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
        }
        
        
    } else if (intent.name === "LastIngredientIntent") {
        // keep saying first ingredient if end
        session.attributes.ingredientNumber -= 1;
        if (session.attributes.ingredientNumber < 0) {
            session.attributes.ingredientNumber = 0;
        }
        speechOutput = session.attributes.ingredients[session.attributes.ingredientNumber]; // get ingredient in index
        var repromptText = speechOutput;
        var shouldEndSession = false;
        callback(session.attributes,
                buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));

    } else if (intent.name === "RestartIntent") {
        // reset
        session.attributes.ingredientNumber = 0;
        if (session.attributes.ingredientNumber < session.attributes.ingredients.length) {  // TODO: don't know if this is how you get array length in js
            speechOutput = session.attributes.ingredients[session.attributes.ingredientNumber]; // get ingredient in index
            var repromptText = speechOutput;
            var shouldEndSession = false;
            // increment index
            session.attributes.ingredientNumber += 1;
            if (session.attributes.ingredientNumber == session.attributes.ingredients.length) { // DON'T TRANSITION TO RECIPE YET. Just say it's the last ingredient, say next ingredient to move to Recipe. Or say last ingredient.
                speechOutput += ". That was the last ingredient. Say next ingredient to move to Recipe Steps, or say last ingredient, or start again.";
                var repromptText = "You can say next ingredient, last ingredient, or start again"
            }
            callback(session.attributes,
                buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
        } else {
            // TRANSITION TO RECIPE STEPS/directions
            session.attributes.currentDialog = "directions";
            speechOutput = "To start hearing recipe directions, say read recipe or start.";
            var repromptText = "To hear recipe directions, say read recipe or start.";
            var shouldEndSession = false;
            callback(session.attributes,
                buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
        }
    }
}


// Directions dialog
function nextStep(session, callback) {
    var index = session.attributes.directionNumber;
    index = index + 1;
    if (index >= session.attributes.directions.length) {
        index = 0;
    }
    session.attributes.directionNumber = index;

    var speechOutput = session.attributes.directions[index];

    callback(session.attributes,
        buildSpeechletResponseWithoutCard(speechOutput, speechOutput, false));
}

function lastStep(session, callback) {
    var index = session.attributes.directionNumber;
    index = index - 1;
    if (index < 0) {
        index = 0;
    }
    session.attributes.directionNumber = index;
    
    var speechOutput = session.attributes.directions[index];

    callback(session.attributes,
        buildSpeechletResponseWithoutCard(speechOutput, speechOutput, false));

}

function firstStep(session, callback) {

    session.attributes.directionNumber = 0;
    var speechOutput = session.attributes.directions[0];
    callback(session.attributes,
        buildSpeechletResponseWithoutCard(speechOutput, speechOutput, false));
}
 
 
function handleRepeatRequest(intent, session, callback) {
    // Repeat the previous speechOutput and repromptText from the session attributes if available
    // else start a new game session
    if (!session.attributes || !session.attributes.speechOutput) {
        getWelcomeResponse(callback);
    } else {
        callback(session.attributes,
            buildSpeechletResponseWithoutCard(session.attributes.speechOutput, session.attributes.repromptText, false));
    }
}
 
function handleGetHelpRequest(intent, session, callback) {
    // Provide a help prompt for the user, explaining how the game is played. Then, continue the game
    // if there is one in progress, or provide the option to start another one.
    
    // Ensure that session.attributes has been initialized
    if (!session.attributes) {
        session.attributes = {};
    }
 
    // Set a flag to track that we're in the Help state.
    // session.attributes.userPromptedToContinue = true;
 
    // Do not edit the help dialogue. This has been created by the Alexa team to demonstrate best practices.
 
    var speechOutput = "You can state which recipe you'd like to make or quit the app.",
        repromptText = "You can state which recipe you'd like to make or quit the app.";
        var shouldEndSession = false;
    callback(session.attributes,
        buildSpeechletResponseWithoutCard(speechOutput, repromptText, shouldEndSession));
}
 
function handleFinishSessionRequest(intent, session, callback) {
    // End the session with a "Good bye!" if the user wants to quit the game
    callback(session.attributes,
        buildSpeechletResponseWithoutCard("Good bye!", "", true));
}

 
// ------- Helper functions to build responses -------
 
 
function buildSpeechletResponse(title, output, repromptText, shouldEndSession) {
    return {
        outputSpeech: {
            type: "PlainText",
            text: output
        },
        card: {
            type: "Simple",
            title: title,
            content: output
        },
        reprompt: {
            outputSpeech: {
                type: "PlainText",
                text: repromptText
            }
        },
        shouldEndSession: shouldEndSession
    };
}
 
function buildSpeechletResponseWithoutCard(output, repromptText, shouldEndSession) {
    return {
        outputSpeech: {
            type: "PlainText",
            text: output
        },
        reprompt: {
            outputSpeech: {
                type: "PlainText",
                text: repromptText
            }
        },
        shouldEndSession: shouldEndSession
    };
}
 
function buildResponse(sessionAttributes, speechletResponse) {
    return {
        version: "1.0",
        sessionAttributes: sessionAttributes,
        response: speechletResponse
    };
}
 



