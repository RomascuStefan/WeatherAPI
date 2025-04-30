import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogContent } from '@angular/material/dialog';
import { MatCard, MatCardContent } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { WeatherResponse } from '../model/weather-response';

@Component({
  selector: 'app-dialog',
  standalone: true,
  imports: [
    MatDialogContent,
    MatCard,
    MatCardContent,
    CommonModule // ✅ importă directivele și pipe-urile de bază
  ],
  templateUrl: './dialog.component.html',
  styleUrl: './dialog.component.css',
})
export class DialogComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA)
    public data: {
      weatherResponse: WeatherResponse;
    }
  ) {}
}
