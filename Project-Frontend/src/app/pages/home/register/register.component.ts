import {Component, inject} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {RegisterDto} from '../../../models/register.model';
import {AnswerDto} from '../../../models/answer.model';

@Component({
  selector: 'home-register',
  imports: [FormsModule],
  templateUrl: 'register.component.html',
  styleUrl: 'register.component.css'
})

export class RegisterComponent {

  showPassword = false;

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  private http = inject(HttpClient);
  userRegister(form: any): void{
    const credentials: RegisterDto = {
      username:form.value.username,
      password:form.value.password,
      email:form.value.email,
    }

    this.http.post<AnswerDto>('/api/register', credentials).subscribe(
      {
        next: (response) => console.log(response.message)
      }
    )
  }

}
