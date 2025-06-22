import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-error-message',
  template: `
    <div *ngIf="message" class="error-container">
      <mat-icon>error</mat-icon>
      <span>{{ message }}</span>
    </div>
  `,
  styles: [`
    .error-container {
      display: flex;
      align-items: center;
      padding: 1rem;
      background-color: #ffebee;
      color: #c62828;
      border-radius: 4px;
      margin: 1rem 0;
    }
    mat-icon {
      margin-right: 0.5rem;
    }
  `],
  standalone: false
})
export class ErrorMessageComponent {
  @Input() message: string | null = null;
}
