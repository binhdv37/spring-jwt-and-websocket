import { Injectable } from '@angular/core';
declare var SockJS;
declare var Stomp;
@Injectable({
  providedIn: 'root'
})
export class MessageService {

  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzeXNhZG1pbiIsInNjb3BlcyI6WyJVU0VSX1ZJRVciLCJVU0VSX0NSRUFURSIsIlVTRVJfVVBEQVRFIiwiVVNFUl9ERUxFVEUiLCJST0xFX1ZJRVciLCJST0xFX0NSRUFURSIsIlJPTEVfVVBEQVRFIiwiUk9MRV9ERUxFVEUiXSwidXNlcklkIjoiMDcxNTdhMDYtZDJiYi00OTEyLWJmYTMtOGFiMTM2NWIzOGJkIiwidXNlck5hbWUiOiJzeXNhZG1pbiIsImZ1bGxOYW1lIjoic3lzYWRtaW4iLCJlbWFpbCI6InN5c2FkbWluQGdtYWlsLmNvbSIsImVuYWJsZSI6dHJ1ZSwiaXNzIjoibXlhcHBfaXNzdWVyIiwiaWF0IjoxNjUzOTIzODE1LCJleHAiOjE2NTM5MzEwMTV9.RRhohMVAdXHOQYCQiTbfCwNle70YbUHA0heAmnu02HSFHZ5DTOhJJ3SKNrc-d1BifM6cFDfzLC7b8Q5-UJeApQ';

  constructor() {
    this.initializeWebSocketConnection();
  }
  public stompClient;
  public msg = [];
  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8081/api/ws';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/topic/greeting', (message) => {
        if (message.body) {
          that.msg.push(message.body);
        }
      });
    });
  }

  sendMessage(message) {
    this.stompClient.send('/app/greeting' , {}, message);
  }
}
