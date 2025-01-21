import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JwtService } from "../service/jwt.service";

@Injectable() // abysmy mogli wstrzykwiac nasz interceptor
export class JwtInterceptor implements HttpInterceptor{

    constructor(private jwtService: JwtService){

    }
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token = this.jwtService.getToken();
        if( token && (
            req.url.startsWith("/api/admin")
            || req.url.startsWith("/api/orders")
            || req.url.startsWith("/api/profiles")
            )){ // nadpisujemy tlyko gdy zaczyna sie od /api/admin
            req = req.clone({ // kolunujemy request i nadpisujemy
                headers: req.headers.set("Authorization", "Bearer " + token)
            })
            // doklejamy token do każdego naszego requestu
        }
        
        return next.handle(req);
    }



}