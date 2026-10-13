import {Component, inject, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {RegisterDto} from '../../../models/register.model';
import {AnswerDto} from '../../../models/answer.model';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'home-register',
  imports: [FormsModule,RouterLink],
  templateUrl: 'register.component.html',
  styleUrl: 'register.component.css'
})

export class RegisterComponent {

  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  private http = inject(HttpClient);
  passwordError = signal('');
  usernameExists=signal('');
  emailExists=signal('');
  registerSuccessful=signal('');
  userRegister(form: any): void{
    const credentials: RegisterDto = {
      username:form.value.username,
      password:form.value.password,
      email:form.value.email,
    }

    this.http.post<AnswerDto>('/api/register', credentials).subscribe(
      {
        next: (response) => {
          console.log(response.message);
          this.passwordError.set('');
          this.usernameExists.set('');
          this.emailExists.set('');
          this.registerSuccessful.set('');
          switch (response.message) {
            case 'Username already exists':{
              this.usernameExists.set(response.message);
            }break;
            case 'Invalid password. It should contain at least one smallercase letter, one uppercase letter, a number and a special sign.':{
              this.passwordError.set(response.message);;
            }break;
            case 'Email already registered':{
              this.emailExists.set(response.message);;
            }break;
            case 'User registered successfully':{
              this.registerSuccessful.set(response.message);;
            }
          }
          if(response.message=="Invalid password"){
            this.passwordError.set(response.message);
          }
        }
      }
    )
  }

}
