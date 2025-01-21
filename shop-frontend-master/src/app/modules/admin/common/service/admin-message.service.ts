import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminMessageService {

  messages: Array<string> = [];
  subject = new Subject<Array<string>>(); // Observable 

  constructor() { }

  // walidacja - opcja backendowa 
  add(message: string): void{
    this.clear();
    this.messages.push(message);
    this.subject.next(this.messages); // next - generuje event, zawrtosc to cala tablica message => potem poleci event do obserwatorów ktorzy sa zasubskrybowanu na nasz serwis
  }



  clear(){
    this.messages = [];
  }
  
  // walidacja - opcja backendowa 
  addSpringErrors(error: any): void{
    this.clear();
    console.log(error);
    this.extractMessages(error);
    this.subject.next(this.messages);
  }

  private extractMessages(error: any) {
    if (error.errors?.length > 0) {
      for (let message of error.errors) {
        this.messages.push("Pole: " + message.field + " -> " + message.defaultMessage);
      }
    } else {
      this.messages.push(error.message);
    }
  }
}
