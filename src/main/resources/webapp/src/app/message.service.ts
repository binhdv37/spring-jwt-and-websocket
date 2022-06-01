import { Injectable } from '@angular/core';
declare var SockJS;
declare var Stomp;
@Injectable({
  providedIn: 'root'
})
export class MessageService {

  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzeXNhZG1pbiIsInNjb3BlcyI6WyJVU0VSX1ZJRVciLCJVU0VSX0NSRUFURSIsIlVTRVJfVVBEQVRFIiwiVVNFUl9ERUxFVEUiLCJST0xFX1ZJRVciLCJST0xFX0NSRUFURSIsIlJPTEVfVVBEQVRFIiwiUk9MRV9ERUxFVEUiXSwidXNlcklkIjoiMDcxNTdhMDYtZDJiYi00OTEyLWJmYTMtOGFiMTM2NWIzOGJkIiwidXNlck5hbWUiOiJzeXNhZG1pbiIsImZ1bGxOYW1lIjoic3lzYWRtaW4iLCJlbWFpbCI6InN5c2FkbWluQGdtYWlsLmNvbSIsImVuYWJsZSI6dHJ1ZSwiaXNzIjoibXlhcHBfaXNzdWVyIiwiaWF0IjoxNjU0MDYyNTQwLCJleHAiOjE2NTQwNjk3NDB9.4uhi9c3ya3iAFAyf-QPZ83Ms48erA-R8fI_K7YptqMFnXKDApiMeScFbslwgygHtH1nl1Jdpo6MwUi1V54vAmg';

  constructor() {
    // this.initializeWebSocketConnection();
  }
  public stompClient;
  public msg = [];
  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8081/api/ws';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;
    // tslint:disable-next-line:only-arrow-functions
    this.stompClient.connect(
      {"X-Authorization": this.token}, // custom stomp native header
      function(frame) {
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
