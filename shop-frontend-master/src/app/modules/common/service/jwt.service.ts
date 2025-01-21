import { Injectable } from '@angular/core';
import { jwtDecode } from "jwt-decode";
@Injectable({
  providedIn: 'root'
})
export class JwtService {
  private adminAccess = false;

 

  constructor() { }

  setToken(token: string){
    localStorage.setItem("token", token); // ustawianie token w local storage
  }

  getToken(){
    return localStorage.getItem("token");
  }
  isLoggedIn(): boolean{
    let token = localStorage.getItem("token");
    return token != null && this.notExpired(token);
  }

  private notExpired(token: string): boolean {
    let tokenDecoded = jwtDecode<any>(token);
    return (tokenDecoded.exp * 1000) > new Date().getTime();
  }

  public getAdminAccess(): boolean {
    return this.adminAccess;
  }
  public setAdminAccess(adminAccess: boolean) {
    this.adminAccess = adminAccess;
  }
}
