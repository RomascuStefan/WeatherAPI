import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<string> {
    return this.http.post(
      'http://localhost:8080/api/auth/login',
      { username, password },
      { responseType: 'text' }
    );
  }

  getWeatherInfo(lon: number, lat: number): Promise<any> {
    const queryParams = new URLSearchParams({
      lat: lat.toString(),
      lon: lon.toString(),
    });
    const url = `http://localhost:8080/api/weather?${queryParams.toString()}`;
    return firstValueFrom(this.http.get(url));
  }
}
