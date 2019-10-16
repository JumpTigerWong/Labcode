class Solution{
    func trailingZeroes(_ n:Int)->Int{
        var res=0;
        var temp=n
        while temp>0{
            temp=temp/5
            res+=temp
        }
        return res
    }
}
