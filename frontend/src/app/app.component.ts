import {
  AfterViewInit,
  Component,
  inject,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { AngularOpenlayersModule } from 'ng-openlayers';
import { transform } from 'ol/proj';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { DialogComponent } from './dialog/dialog.component';
import { CommonModule } from '@angular/common';
import { AppService } from './app.service';
import { LoginComponent } from './login/login.component';
import { WeatherResponse } from './model/weather-response';

const SESSION_TIMEOUT_MINUTES = 10;

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    AngularOpenlayersModule,
    MatDialogModule,
    CommonModule,
    LoginComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit, AfterViewInit, OnDestroy {
  title = 'weatherapp-fe';

  lat = 0;
  lon = 0;
  isLoggedIn = false;
  readonly dialog = inject(MatDialog);

  constructor(private appService: AppService) {}

  ngOnInit(): void {
    const auth = localStorage.getItem('auth');
    const sessionStart = localStorage.getItem('session_start');

    if (auth && sessionStart) {
      const now = Date.now();
      const elapsed = now - parseInt(sessionStart, 10);
      const limit = SESSION_TIMEOUT_MINUTES * 60 * 1000;

      this.isLoggedIn = elapsed < limit;
      if (!this.isLoggedIn) this.logout();
    }
  }

  ngAfterViewInit(): void {
    window.addEventListener('click', this.extendSession.bind(this));
  }

  extendSession(): void {
    if (this.isLoggedIn) {
      localStorage.setItem('session_start', Date.now().toString());
    }
  }

  logout(): void {
    localStorage.removeItem('auth');
    localStorage.removeItem('session_start');
    this.isLoggedIn = false;
  }

  async dispatchCursor(event: any): Promise<void> {
    const coordinates = event.coordinate;
    this.lon = transform(coordinates, 'EPSG:3857', 'EPSG:4326')[0];
    this.lat = transform(coordinates, 'EPSG:3857', 'EPSG:4326')[1];

    try {
      const resp = await this.appService.getWeatherInfo(this.lon, this.lat);
      if (resp) {
        this.openDialog(resp);
      }
    } catch (error) {
      console.error(error);
    }
  }

  openDialog(resp: any): void {
    this.dialog.open(DialogComponent, {
      data: { weatherResponse: resp },
    });
  }

  onLoginSuccess(): void {
    const now = Date.now();
    localStorage.setItem('session_start', now.toString());
    this.isLoggedIn = true;
  }

  ngOnDestroy(): void {
    window.removeEventListener('click', this.extendSession.bind(this));
  }
}
