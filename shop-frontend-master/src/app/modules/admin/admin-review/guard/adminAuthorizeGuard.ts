import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { JwtService } from "../../../common/service/jwt.service";
import { Injectable } from "@angular/core";

@Injectable()
export class AdminAuthorizeGuard implements CanActivate{
    
    constructor(private jwtService: JwtService,
        private router: Router){

    }
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        if(!this.jwtService.isLoggedIn() || !this.jwtService.getAdminAccess()){
            this.router.navigate(["/admin/login"]);
        }
        return true;


    }
}