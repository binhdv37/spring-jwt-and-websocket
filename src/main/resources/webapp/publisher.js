StompJs = require('@stomp/stompjs');

const client = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/api/ws',
    connectHeaders: {
        Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzeXNhZG1pbiIsInNjb3BlcyI6WyJVU0VSX1ZJRVciLCJVU0VSX0NSRUFURSIsIlVTRVJfVVBEQVRFIiwiVVNFUl9ERUxFVEUiLCJST0xFX1ZJRVciLCJST0xFX0NSRUFURSIsIlJPTEVfVVBEQVRFIiwiUk9MRV9ERUxFVEUiXSwidXNlcklkIjoiMDcxNTdhMDYtZDJiYi00OTEyLWJmYTMtOGFiMTM2NWIzOGJkIiwidXNlck5hbWUiOiJzeXNhZG1pbiIsImZ1bGxOYW1lIjoic3lzYWRtaW4iLCJlbWFpbCI6InN5c2FkbWluQGdtYWlsLmNvbSIsImVuYWJsZSI6dHJ1ZSwiaXNzIjoibXlhcHBfaXNzdWVyIiwiaWF0IjoxNjUzODQ1MDk0LCJleHAiOjE2NTM4NTIyOTR9.op59JppGg5RAHb4mIKo3FZyMqtcMSrtFOPiecRhVNWI7HQ-jwd6Kn6736y0WJ9Ac78fjV8s_cc8e7taQfBsEnA',
    },
    debug: function (str) {
        console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});

client.onConnect = function (frame) {
    // Do something, all subscribes must be done is this callback
    // This is needed because this will be executed after a (re)connect
    console.log('-- client connected');
};

client.onStompError = function (frame) {
    // Will be invoked in case of error encountered at Broker
    // Bad login/passcode typically will cause an error
    // Complaint brokers will set `message` header with a brief message. Body may contain details.
    // Compliant brokers will terminate the connection after any error
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
};

client.activate();

// client.publish({ destination: '/app/greeting', body: 'Hello world' });

// There is an option to skip content length header
// client.publish({
//     destination: '/app/greeting',
//     body: 'Hello world',
//     skipContentLengthHeader: true,
// });

// Additional headers
// client.publish({
//     destination: '/topic/general',
//     body: 'Hello world',
//     headers: { priority: '9' },
// });
