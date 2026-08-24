import {Component, inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {LoginDto} from '../../../models/login.model';
import {AnswerDto} from '../../../models/answer.model';
import {Router} from '@angular/router';
import {AuthService} from '../../../core/auth/auth.service';


@Component({
  selector: 'home-login',
  imports: [FormsModule],
  templateUrl: 'login.component.html',
  styleUrl: 'login.component.css'
})

export class LoginComponent {
  private authService = inject(AuthService);
  showPassword = false;
  loginError: string | null = null;
  private router = inject(Router);


  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }
  private http = inject(HttpClient);
  userLogin(form: any): void{
     const username=form.value.username
     const password=form.value.password

    this.authService.login(username, password).subscribe({
      next: (response) => {
        if (response.success) {
          this.router.navigate(['/menu']);
        } else {
          alert(response.message);
        }
      },
      error: (err) => {
        console.error(err);
        alert('Login failed');
      }
    });

}

}
