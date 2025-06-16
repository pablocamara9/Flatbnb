import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-announcement-detail',
  standalone: false,
  templateUrl: './announcement-detail.component.html',
  styleUrl: './announcement-detail.component.css'
})
export class AnnouncementDetailComponent implements OnInit {
  anuncio: any;

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.http.get<any>(`http://localhost:8080/anuncio/${id}`).subscribe({
        next: data => this.anuncio = data,
        error: () => this.anuncio = null
      });
    }
  }
}
