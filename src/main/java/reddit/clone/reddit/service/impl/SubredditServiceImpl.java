package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.Subreddit;
import reddit.clone.reddit.exception.NotFoundException;
import reddit.clone.reddit.mapper.SubredditMapper;
import reddit.clone.reddit.repository.SubredditRepository;
import reddit.clone.reddit.service.SubredditService;
import reddit.clone.reddit.vm.subreddit.SubredditCreateRequestVM;
import reddit.clone.reddit.vm.subreddit.SubredditResponseVM;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubredditServiceImpl implements SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    @Override
    public void createSubreddit(SubredditCreateRequestVM subredditCreateRequestVM) {
        subredditRepository.save(
                subredditMapper.createRequestVmToEntity(subredditCreateRequestVM)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Set<SubredditResponseVM> getAllSubreddits(Pageable pageable) {
        return subredditRepository.findAll(pageable)
                .stream()
                .map(subredditMapper::entityToResponseVM)
                .collect(Collectors.toSet());
    }

    @Override
    public SubredditResponseVM getSubredditById(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new NotFoundException("No subreddit found with id: " + subredditId));
        return subredditMapper.entityToResponseVM(subreddit);
    }

}
