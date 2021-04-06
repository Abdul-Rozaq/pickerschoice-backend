package com.pickerschoice.pickerschoice.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pickerschoice.pickerschoice.exception.AppAuthException;
import com.pickerschoice.pickerschoice.model.ConfirmationToken;
import com.pickerschoice.pickerschoice.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Transactional
    public ConfirmationToken saveConfirmationToken(ConfirmationToken token) {
        return confirmationTokenRepository.save(token);
    }

    @Transactional
    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new AppAuthException("Token does not exist"));
    }
    
    @Transactional
    public int updateConfirmation(String token) {
    	LocalDateTime confirmedAt = LocalDateTime.now();
    	
    	return confirmationTokenRepository.updateConfirmation(token, confirmedAt);
    }
}
