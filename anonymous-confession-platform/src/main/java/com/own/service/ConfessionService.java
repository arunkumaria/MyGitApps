package com.own.service;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.own.dto.ConfessionRequest;
import com.own.dto.ConfessionResponse;
import com.own.entity.Confession;
import com.own.entity.ConfessionStatus;
import com.own.entity.User;
import com.own.repository.ConfessionRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ConfessionService {

    private final ConfessionRepository confessionRepository;

    public void submit(ConfessionRequest request, User user, String ip, String userAgent) {

        Confession confession = Confession.builder()
                .content(request.getContent())
                .category(request.getCategory())
                .status(ConfessionStatus.PENDING)
                .user(user)
                .ipHash(hash(ip))
                .deviceHash(hash(userAgent))
                .build();

        confessionRepository.save(confession);
    }

    public List<ConfessionResponse> getApproved() {
        return confessionRepository.findByStatus(ConfessionStatus.APPROVED)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private ConfessionResponse mapToDto(Confession c) {
        return new ConfessionResponse(
                c.getId(),
                c.getContent(),
                c.getCategory(),
                c.getCreatedAt()
        );
    }

    private String hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}