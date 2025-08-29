import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MessageService } from 'primeng/api';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const messageService = inject(MessageService);

  return next(req).pipe(
    catchError((error) => {
      let message = 'Une erreur est survenue';

      if (error.error?.message) {
        message = error.error.message;
      } else if (error.status === 0) {
        message = 'Impossible de contacter le serveur';
      } else if (error.status >= 500) {
        message = 'Erreur serveur. Réessayez plus tard.';
      }

      messageService.add({
        severity: 'error',
        summary: 'Erreur',
        detail: message
      });

      return throwError(() => error);
    })
  );
};