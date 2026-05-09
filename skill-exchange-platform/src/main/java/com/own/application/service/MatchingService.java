package com.own.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.own.domain.model.User;
import com.own.domain.model.UserSkill;
import com.own.domain.repository.UserSkillRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MatchingService {

	private final UserSkillRepository repo;

	public List<User> findMatches(User user) {

		// 1️⃣ Get all skills this user wants to learn
		List<UserSkill> learnSkills = repo.findByUserId(user.getId());

		// 2️⃣ Get all skills this user can teach
		List<UserSkill> teachSkills = repo.findSkillsUserCanTeach(user.getId());

		List<User> matches = new ArrayList<>();

		// 3️⃣ For each skill the user wants to learn, find users who can teach it
		for (UserSkill learn : learnSkills) {

			List<User> usersWhoCanTeach = repo.findUsersTeachingSkill(learn.getLearnSkill());

			// Optional: remove self from matches
			usersWhoCanTeach.removeIf(u -> u.getId().equals(user.getId()));

			matches.addAll(usersWhoCanTeach);
		}

		return matches;
	}
}