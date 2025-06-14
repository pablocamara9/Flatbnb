import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Anuncio, Anuncios } from '../../models/anuncio.model';

@Component({
  selector: 'app-main-page',
  standalone: false,
  templateUrl: './main-page.component.html',
  styleUrl: './main-page.component.css'
})
export class MainPageComponent implements OnInit {
  anuncios: Anuncio[] = [];

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.http.get<any>('http://localhost:8080/anuncio/')
      .subscribe({
        next: (data) => {
          console.log(data);
          
          if (data && data.content) {
            this.anuncios = data.content;
            console.log(this.anuncios);
            
          } else {
            this.anuncios = [];
          }
        },
        error: (err) => {
          this.anuncios = [];
        }
      });
  }

  verDetalle(id: string) {
    this.router.navigate(['/anuncio', id]);
  }

}
