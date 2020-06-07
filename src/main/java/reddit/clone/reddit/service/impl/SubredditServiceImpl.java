package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.Subreddit;
import reddit.clone.reddit.exception.BadRequestException;
import reddit.clone.reddit.exception.NotFoundException;
import reddit.clone.reddit.mapper.SubredditMapper;
import reddit.clone.reddit.repository.SubredditRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.SubredditService;
import reddit.clone.reddit.vm.subreddit.SubredditCreateRequestVM;
import reddit.clone.reddit.vm.subreddit.SubredditResponseVM;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SubredditServiceImpl implements SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final AuthService authService;

    @Transactional
    @Override
    public void createSubreddit(SubredditCreateRequestVM subredditCreateRequestVM) {
        try {
            subredditRepository.save(
                    subredditMapper.createRequestVmToEntity(subredditCreateRequestVM, authService.getCurrentUser())
            );
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Set<SubredditResponseVM> getAllSubreddits(Pageable pageable) {
        return subredditRepository.findAll(pageable)
                .stream()
                .map(subredditMapper::entityToResponseVM)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public SubredditResponseVM getSubredditById(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new NotFoundException("No subreddit found with id: " + subredditId));
        return subredditMapper.entityToResponseVM(subreddit);
    }

}
