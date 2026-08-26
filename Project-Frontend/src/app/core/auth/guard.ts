import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    const token= authService.getToken();
    if (token) {
      if(!authService.isExpired(token)){
        return true
      }
    }
  }

  authService.logout();
  router.navigate(['/login']);
  return false;
};
