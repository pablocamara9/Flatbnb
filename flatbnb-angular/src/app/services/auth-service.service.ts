import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
//import { jwtDecode } from 'jwt-decode';
import { User } from '../models/user.model';
import { jwtDecode } from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient) { }

  getRoles(): string {
    const userStr = localStorage.getItem('user') || sessionStorage.getItem('user');
    if (userStr) {
      try {
        const user = JSON.parse(userStr);
        if (user.token) {
          const decoded: any = jwtDecode(user.token);
          if (decoded.roles && Array.isArray(decoded.roles) && decoded.roles.length > 0) {
            return decoded.roles[0];
          }
          if (typeof decoded.roles === 'string') {
            return decoded.roles;
          }
        }
      } catch (e) {
        console.error('Error decodificando el token:', e);
      }
    }
    return '';
  }

}