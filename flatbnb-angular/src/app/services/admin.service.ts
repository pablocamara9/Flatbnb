import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  eliminarUsuario(id: string) {
    const token = localStorage.getItem('accessToken');
    let headers = undefined;
    if (token) {
      headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    return this.http.delete(`http://localhost:8080/user/${id}`, { headers });
  }

  editarUsuario(id: string) {
    const token = localStorage.getItem('accessToken');
    let headers = undefined;
    if (token) {
      headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    }
    return this.http.put(`http://localhost:8080/user/${id}`, { headers });
  }
}
