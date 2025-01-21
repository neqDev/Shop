import { Component, OnDestroy, OnInit } from '@angular/core';
import { AdminMessageService } from '../../service/admin-message.service';

@Component({
  selector: 'app-admin-message',
  templateUrl: './admin-message.component.html',
  styleUrls: ['./admin-message.component.scss']
})
export class AdminMessageComponent implements OnInit, OnDestroy{

  messages: Array<string> = [];
  private clickCounter: number = 0;
  constructor(private adminMessageService: AdminMessageService){}


  ngOnInit(): void {
    this.adminMessageService.subject.subscribe( // subskrybujemy sie do naszego observable
      messages => {
        this.messages = messages;
        this.timeoutCloseMssages();
      }
      )}


  ngOnDestroy(): void {
    this.adminMessageService.subject.unsubscribe(); // od subskrybowanie z submita
  }

  clearMessages(){
    this.messages = [];
    this.adminMessageService.clear();
  }
  
  private timeoutCloseMssages() {
    this.clickCounter++;
    // komunikat błędów pola zamknie się automatycznie po 5s
    setTimeout(() => {
      if (this.clickCounter == 1) { // flaga aby timeout byl liczony od ostatniego klikniecia

        this.clearMessages();
      }
      this.clickCounter--;
    }, 12000);
  }}
