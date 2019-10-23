func rob(_ nums: [Int]) -> Int {
    var prevNum=0
    var currNum=0
    for n in nums{
        let temp=currNum
        currNum=max(prevNum+n, currNum)
        prevNum=temp
    }
    return currNum
}
