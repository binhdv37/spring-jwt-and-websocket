import {Component, OnInit} from '@angular/core';
import {MessageService} from './message.service';
import {FormBuilder, FormGroup} from "@angular/forms";

declare var SockJS;
declare var Stomp;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'websocket-frontend';

  formGroup: FormGroup;

  stompClient: any;
  msg = [];

  connected = false;
  subscribed = false;
  subscribedTopic = '';

  constructor(
    private messageService: MessageService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.formGroup = this.fb.group({
      token: [''],
      subscribeTopic: [''],
      sendMessageEndpoint: [''], // end point for send message
      message: [''] // message to send
    });
  }

  connect() {
    const token = this.formGroup.get('token').value;
    if (token) {
      const serverUrl = 'http://localhost:8081/api/ws';
      const ws = new SockJS(serverUrl);
      this.stompClient = Stomp.over(ws);
      const that = this;
      // tslint:disable-next-line:only-arrow-functions
      this.stompClient.connect(
        {"X-Authorization": token}, // custom stomp native header
        () => {
          this.connected = true;
        } // on connect success callback
      );
    } else {
      console.error('-- Token is required');
    }
  }

  send() {
    const message = this.formGroup.get('message').value;
    const sendMessageEndpoint = this.formGroup.get('sendMessageEndpoint').value;
    if (message && sendMessageEndpoint) {
      this.stompClient.send(sendMessageEndpoint , {}, message);
    } else {
      console.error('-- Message or endpoint have not defined yet');
    }
  }

  subscribe() {
    const subscribeTopic = this.formGroup.get('subscribeTopic').value;
    if (subscribeTopic) {
      this.stompClient.subscribe(subscribeTopic, (message) => {
        console.log('-- Receive message: ', message);
        if (message.body) {
          this.msg.push(message.body);
        }
      });
      this.subscribed = true;
      this.subscribedTopic = subscribeTopic;
    } else {
      console.error('-- Subscribe topic have not defined yet!');
    }
  }

}
