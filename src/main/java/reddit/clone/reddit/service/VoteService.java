package reddit.clone.reddit.service;

import reddit.clone.reddit.vm.vote.VoteCreateVM;

public interface VoteService {

    void vote(VoteCreateVM voteCreateVM);

}
