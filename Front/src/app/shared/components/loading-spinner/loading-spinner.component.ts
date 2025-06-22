import { Component } from '@angular/core';

@Component({
  selector: 'app-loading-spinner',
  template: `
    <div class="loading-container">
      <mat-spinner diameter="50"></mat-spinner>
      <p class="loading-text">Loading...</p>
    </div>
  `,
  styles: [`
    .loading-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 2rem;
    }
    .loading-text {
      margin-top: 1rem;
      color: #666;
    }
  `],
  standalone: false
})
export class LoadingSpinnerComponent {}
