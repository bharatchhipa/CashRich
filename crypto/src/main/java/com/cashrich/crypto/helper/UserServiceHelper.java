package com.cashrich.crypto.helper;

import com.cashrich.crypto.validator.Validator;
import com.cashrich.crypto.constants.ValidationMessages;
import com.cashrich.crypto.dto.request.UpdateUserRequest;
import com.cashrich.crypto.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceHelper {

    public void validateUserUpdateRequest(UpdateUserRequest updateUserRequest){
        if(updateUserRequest.getFirstName()!=null && updateUserRequest.getFirstName().isEmpty()){
            throw new BadRequestException(ValidationMessages.FIST_NAME_REQUIRED);
        }

        if(updateUserRequest.getLastName()!=null && updateUserRequest.getLastName().isEmpty()){
            throw new BadRequestException(ValidationMessages.LAST_NAME_REQUIRED);
        }

        if(updateUserRequest.getMobile()!=null && updateUserRequest.getMobile().isEmpty()){
            throw new BadRequestException(ValidationMessages.MOBILE_REQUIRED);
        }

        if(updateUserRequest.getMobile()!=null && !updateUserRequest.getMobile().isEmpty()){
            if(!Validator.isMobileValid(updateUserRequest.getMobile())){
                throw new BadRequestException(ValidationMessages.MOBILE_INVALID);
            }
        }
        if(updateUserRequest.getPassword()!=null && updateUserRequest.getPassword().isEmpty()){
            throw new BadRequestException(ValidationMessages.PASSWORD_REQUIRED);
        }

        if(updateUserRequest.getPassword()!=null && !updateUserRequest.getPassword().isEmpty()){
            if(!Validator.isPasswordValid(updateUserRequest.getPassword())){
                throw new BadRequestException(ValidationMessages.PASSWORD_INVALID);
            }
        }

    }
}
